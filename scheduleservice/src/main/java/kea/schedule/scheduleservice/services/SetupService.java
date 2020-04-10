package kea.schedule.scheduleservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kea.schedule.scheduleservice.models.Group;
import net.minidev.json.parser.JSONParser;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import net.minidev.json.JSONObject;
import kea.schedule.scheduleservice.models.User;
@Service
public class SetupService {
    @Value("${infrastructure.port:7500}")
    int infrastructureport;

    @Value("${ms.port.service:7510}")
    int serviceport;

    private UserService userservice;
    private GroupService groupservice;

    public SetupService(UserService userservice, GroupService groupservice){
        this.userservice = userservice;
        this.groupservice = groupservice;
    }

    public void setup() {
        //System.out.println("Synchronize users.... this might take a while");
        //synchronizeUsers();
        System.out.println("Synchronize groups");
        synchronizeGroups();
        System.out.println("Setup admin ms");
        setupAdminMS();
        System.out.println("Setup teacher ms");
        setupTeacherMS();
        System.out.println("Setup student ms");
        setupStudentMS();
    }

    private void synchronizeUsers() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<User[]> response =
                restTemplate.getForEntity(
                        "http://localhost:7500/find/all/User/",
                        User[].class);
        User[] users = response.getBody();
        for(User usr : users){
            System.out.println(usr.getIdentifier());
            userservice.create(usr);
        }
    }

    private void synchronizeGroups() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Group[]> response =
                restTemplate.getForEntity(
                        "http://localhost:7500/find/all/Groups/",
                        Group[].class);
        Group[] groups = response.getBody();
        for(Group grp : groups){
            System.out.println(grp.getName());
            groupservice.create(grp);
        }
        /*
        ResponseEntity<JSONObject> response =
                restTemplate.getForEntity(
                        "http://localhost:7500/find/all/Group/",
                        JSONObject.class);
        JSONObject json = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        for(String key: json.keySet()){
            System.out.println(json.get(key));
            try {
                Group grp = mapper.readValue(((JSONObject)json.get(key)).toJSONString(), Group.class);
                System.out.println("grp object: " + grp.toJSON(new JSONObject()).toString());
            } catch (JsonProcessingException e) {
                System.out.println("Failed to convert");
                e.printStackTrace();
            }
        }

         */
        /*
        Group[] groups = response.getBody();
        for(Group grp : groups){
            System.out.println(grp.getName());
            groupservice.create(grp);
        }

         */
    }


    private void setupTeacherMS() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity;
        JSONObject json;
        json = new JSONObject();
        json.appendField("name", "KEA-Schedule-Teacher");
        json.appendField("port", 7512);
        json.appendField("enabled", false);
        json.appendField("description", "Teacher module");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceregistration", HttpMethod.POST, entity, String.class);

        //Add topmenulink eng
        json = new JSONObject();
        json.appendField("path", "https://localhost/servicepages/scheduleserviceteacher/index.eng");
        json.appendField("text", "Schedule");
        json.appendField("language", "eng");
        json.appendField("description", "");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddtopmenulink/KEA-Schedule-Teacher", HttpMethod.POST, entity, String.class);
        //Add topmenulink dk
        json = new JSONObject();
        json.appendField("path", "https://localhost/servicepages/scheduleserviceteacher/index.dk");
        json.appendField("text", "Skema");
        json.appendField("language", "dk");
        json.appendField("description", "");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddtopmenulink/KEA-Schedule-Teacher", HttpMethod.POST, entity, String.class);

        //Add topmenulink eng
        json = new JSONObject();
        json.appendField("path", "https://localhost/servicepages/scheduleserviceteacher/upload.eng");
        json.appendField("text", "Upload schedule");
        json.appendField("language", "eng");
        json.appendField("description", "");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddtopmenulink/KEA-Schedule-Teacher", HttpMethod.POST, entity, String.class);
        //Add topmenulink dk
        json = new JSONObject();
        json.appendField("path", "https://localhost/servicepages/scheduleserviceteacher/upload.dk");
        json.appendField("text", "Upload skema");
        json.appendField("language", "dk");
        json.appendField("description", "");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddtopmenulink/KEA-Schedule-Teacher", HttpMethod.POST, entity, String.class);
    }

    private void setupStudentMS() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity;
        JSONObject json;
        json = new JSONObject();
        json.appendField("name", "KEA-Schedule-Student");
        json.appendField("port", 7511);
        json.appendField("enabled", false);
        json.appendField("description", "Teacher module");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceregistration", HttpMethod.POST, entity, String.class);

        //Add topmenulink eng
        json = new JSONObject();
        json.appendField("path", "https://localhost/servicepages/scheduleservicestudent/index.eng");
        json.appendField("text", "Schedule");
        json.appendField("language", "eng");
        json.appendField("description", "");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddtopmenulink/KEA-Schedule-Student", HttpMethod.POST, entity, String.class);
        //Add topmenulink dk
        json = new JSONObject();
        json.appendField("path", "https://localhost/servicepages/scheduleservicestudent/index.dk");
        json.appendField("text", "Skema");
        json.appendField("language", "dk");
        json.appendField("description", "");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddtopmenulink/KEA-Schedule-Student", HttpMethod.POST, entity, String.class);
    }

    private void setupAdminMS() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity;
        JSONObject json;
        json = new JSONObject();
        json.appendField("name", "KEA-Schedule-Admin");
        json.appendField("port", 7510);
        json.appendField("enabled", false);
        json.appendField("description", "Admin module");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceregistration", HttpMethod.POST, entity, String.class);

        //Add topmenulink eng
        json = new JSONObject();
        json.appendField("path", "https://localhost/servicepages/scheduleserviceadmin/index.eng");
        json.appendField("text", "Schedule admin");
        json.appendField("language", "eng");
        json.appendField("description", "");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddtopmenulink/KEA-Schedule-Admin", HttpMethod.POST, entity, String.class);
        //Add topmenulink dk
        json = new JSONObject();
        json.appendField("path", "https://localhost/servicepages/scheduleserviceadmin/index.dk");
        json.appendField("text", "Skema admin");
        json.appendField("language", "dk");
        json.appendField("description", "");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddtopmenulink/KEA-Schedule-Admin", HttpMethod.POST, entity, String.class);

        //Action: GroupService
        json = new JSONObject();
        json.appendField("callbackurl", "http://localhost:" + serviceport + "/actions/group/create/");
        json.appendField("actionname", "GroupService.create");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddaction/KEA-Schedule-Admin", HttpMethod.POST, entity, String.class);
        json = new JSONObject();
        json.appendField("callbackurl", "http://localhost:" + serviceport + "/actions/group/edit/");
        json.appendField("actionname", "GroupService.edit");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddaction/KEA-Schedule-Admin", HttpMethod.POST, entity, String.class);
        json = new JSONObject();
        json.appendField("callbackurl", "http://localhost:" + serviceport + "/actions/group/delete/");
        json.appendField("actionname", "GroupService.delete");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddaction/KEA-Schedule-Admin", HttpMethod.POST, entity, String.class);

        //Action: GroupService
        json = new JSONObject();
        json.appendField("callbackurl", "http://localhost:" + serviceport + "/actions/user/create/");
        json.appendField("actionname", "UserService.create");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddaction/KEA-Schedule-Admin", HttpMethod.POST, entity, String.class);
        json = new JSONObject();
        json.appendField("callbackurl", "http://localhost:" + serviceport + "/actions/user/edit/");
        json.appendField("actionname", "UserService.edit");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddaction/KEA-Schedule-Admin", HttpMethod.POST, entity, String.class);
        json = new JSONObject();
        json.appendField("callbackurl", "http://localhost:" + serviceport + "/actions/user/delete/");
        json.appendField("actionname", "UserService.delete");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddaction/KEA-Schedule-Admin", HttpMethod.POST, entity, String.class);
    }


}