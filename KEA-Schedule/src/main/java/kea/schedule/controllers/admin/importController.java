package kea.schedule.controllers.admin;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import kea.schedule.moduls.User;
import kea.schedule.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("admin/import/")
public class importController {
    private UserService userservice;

    @Autowired
    public importController(UserService userservice){
        this.userservice = userservice;
    }
    @GetMapping({"", "index"})
    public String get_index(){
        return "admin/import/index";
    }

    @PostMapping("users")
    public String post_users(@RequestParam(name="usercsv") MultipartFile usercsv){
        try {
            System.out.println(usercsv.getOriginalFilename());
            CsvSchema bootstrapSchema = CsvSchema.builder().addColumn("displayname").addColumn("identifier").build();
            bootstrapSchema.skipsFirstDataRow();
            bootstrapSchema.withColumnSeparator(',');
            bootstrapSchema.rebuild();
            CsvMapper mapper = new CsvMapper();

            System.out.println(usercsv.getOriginalFilename());
            System.out.println(usercsv.getOriginalFilename());
            MappingIterator<User> readValues =
                    mapper.readerFor(User.class).with(bootstrapSchema).readValues(usercsv.getBytes());
            List<User> users = readValues.readAll();
            System.out.println(usercsv.getOriginalFilename());
            for(User usr : users){
                System.out.println(usr.getDisplayname() + " " + usr.getIdentifier());
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
    public String post_groups(MultipartFile file){
        return "admin/import/groups";
    }
}
