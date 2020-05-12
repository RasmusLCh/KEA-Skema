package microservice.infrastructure.controllers.admin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import microservice.infrastructure.models.Group;
import microservice.infrastructure.models.User;
import microservice.infrastructure.services.AuthenticationService;
import microservice.infrastructure.services.GroupService;
import microservice.infrastructure.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/import/")
public class importController {
    private UserService userservice;
    private GroupService grpservice;
    private AuthenticationService authservice;

    @Autowired
    public importController(UserService userservice, GroupService grpservice, AuthenticationService authservice){
        this.userservice = userservice;
        this.grpservice = grpservice;
        this.authservice = authservice;
    }
    @GetMapping({"", "index"})
    public String get_index(){
        return "admin/import/index";
    }

    @PostMapping("users")
    public String post_users(@RequestParam(name="usercsv") MultipartFile usercsv){
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        try {
            System.out.println(usercsv.getOriginalFilename());
            CsvSchema bootstrapSchema = CsvSchema.builder()
                                            .addColumn("displayname")
                                            .addColumn("email")
                                            .addColumn("identifier")
                                            .setSkipFirstDataRow(true)
                                            .build().withColumnSeparator(',');
            CsvMapper mapper = new CsvMapper();

            System.out.println(usercsv.getOriginalFilename());
            System.out.println(usercsv.getOriginalFilename());
            MappingIterator<User> readValues =
                    mapper.readerFor(User.class).with(bootstrapSchema).readValues(usercsv.getBytes());
            List<User> users = readValues.readAll();
            System.out.println(usercsv.getOriginalFilename());
            for(User usr : users){
                System.out.println("Displayname: " + usr.getDisplayname() + " Email: " + usr.getEmail() + " Identifier: " + usr.getIdentifier());
                usr.setLanguage("ENG");
                userservice.create(usr);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            //logger.error("Error occurred while loading object list from file " + fileName, e);
            //return Collections.emptyList();
        }
        return "admin/import/users";
    }

    @PostMapping("groups")
    public String post_groups(@RequestParam(name="grpjson") MultipartFile file){
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        try {
            ObjectMapper jsonMapper = new ObjectMapper();
            JsonNode jsonNode = jsonMapper.readTree(file.getBytes());
            Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields();
            //Run through all groups in JSON file
            while (it.hasNext()) {
                Map.Entry<String,JsonNode> entry = it.next();
                String name = entry.getKey();
                if(name != null){
                    //System.out.println(name);
                    List<Group> grps = grpservice.findByMetadata(name);
                    //Check if any groups has the group name defined in their metadata, if so run through the grps
                    for(Group grp : grps){
                        System.out.println(grp.getName() + " " + grp.getMetadata());
                        List<User> userlist = new ArrayList();
                        Iterator<Map.Entry<String, JsonNode>> grpuserident = entry.getValue().fields();
                        //Add users to the group list
                        while(grpuserident.hasNext()){
                            Map.Entry<String,JsonNode> userentry = grpuserident.next();
                            String userident = userentry.getKey();
                            User user = userservice.findByIdentifier(userident);
                            if(user != null){
                                userlist.add(user);
                            }
                            System.out.println("\t" + userident);
                        }
                        //Set users of group and save list.
                        grp.setUsers(userlist);
                        grpservice.edit(grp);
                    }
                }
            }

        }
        catch (IOException e){
            System.out.println("post_groups: " + e.getMessage());
        }
        return "admin/import/groups";
    }
}
