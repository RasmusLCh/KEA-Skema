package kea.schedule.scheduleservice.services;

import kea.schedule.scheduleservice.models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import net.minidev.json.JSONObject;
import kea.schedule.scheduleservice.models.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class SetupService {
    @Value("${infrastructure.port:7500}")
    int infrastructureport;

    @Value("${ms.port.service:7510}")
    int serviceport;

    private UserService userservice;
    private GroupService groupservice;
    private ResourceLoader rl;

    @Autowired
    public SetupService(UserService userservice, GroupService groupservice, ResourceLoader rl){
        this.userservice = userservice;
        this.groupservice = groupservice;
        this.rl = rl;
    }

    public void install() {
        System.out.println("Synchronize users.... this might take a while");
        synchronizeUsers();
        System.out.println("Synchronize groups");
        synchronizeGroups();
        System.out.println("Setup admin ms");
        setupAdminMS();
        System.out.println("Setup teacher ms");
        setupTeacherMS();
        System.out.println("Setup schedule ms");
        setupScheduleMS();
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
            //We dont want to import any groups, since they are just ids
            usr.setGroups(new ArrayList());
            if(userservice.findById(usr.getId()) == null){
                userservice.create(usr);
            }
            else{
                userservice.edit(usr);
            }
        }
    }

    private void synchronizeGroups() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Group[]> response =
                restTemplate.getForEntity(
                        "http://localhost:7500/find/all/Group/",
                        Group[].class);
        Group[] groups = response.getBody();
        for(Group grp : groups){
            grp.setGroups(new ArrayList<>());
            //We want this creation of groups to take as short as possible, so we omit users
            grp.setUsers(new ArrayList<>());
            if(groupservice.findById(grp.getId()) == null){
                groupservice.create(grp);
            }
            else{
                //Notting needed we update with all other info in next step
            }
        }
        //Now all groups are loaded, so we can add the groups that are inside other groups
        //We are forced to reload, or we could have cloned initial groups.
        //Potential issue if more groups are added inbetween theese 2 operations!
        response =
                restTemplate.getForEntity(
                        "http://localhost:7500/find/all/Group/",
                        Group[].class);
        groups = response.getBody();
        for(Group grp : groups){
            List<Group> grpsingrp = new ArrayList();
            //Convert the groups that only has an ID to group groups.
            for(Group gid : grp.getGroups()){
                Group gf = groupservice.findById(gid.getId());
                if(gf != null){
                    grpsingrp.add(gf);
                }
            }
            grp.setGroups(grpsingrp);

            //Convert the users that only has an ID to group users.
            List<User> usersingrp = new ArrayList();
            for(User usr : grp.getUsers()){
                User uf = userservice.findById(usr.getId());
                if(uf != null){
                    usersingrp.add(uf);
                }
            }
            grp.setUsers(usersingrp);

            groupservice.edit(grp);
        }
    }


    public void setupTeacherMS() {
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
        json.appendField("path", "/servicepages/KEA-Schedule-Teacher/index.eng");
        json.appendField("text", "Manage courses");
        json.appendField("priority", "90");
        json.appendField("language", "eng");
        json.appendField("description", "");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddtopmenulink/KEA-Schedule-Teacher", HttpMethod.POST, entity, String.class);
        //Add topmenulink dk
        json = new JSONObject();
        json.appendField("path", "/servicepages/KEA-Schedule-Teacher/index.dk");
        json.appendField("text", "Administrer kurser");
        json.appendField("priority", "90");
        json.appendField("language", "dk");
        json.appendField("description", "");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddtopmenulink/KEA-Schedule-Teacher", HttpMethod.POST, entity, String.class);

        //Lets upload our print picture
        File file = null;
        try {
            Resource res = rl.getResource("classpath:static/qa_teacher.png");
            file = res.getFile();
            if(file == null) throw new IOException("File is null");
            byte[] filecontent = Files.readAllBytes(file.toPath());
            MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
            fileMap.add(HttpHeaders.CONTENT_DISPOSITION, "form-data; name=\"multipartfile\"; filename=\"qa_teacher.png\"");
            fileMap.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(file.toPath()));
            HttpEntity<byte[]> fileentity = new HttpEntity<>(filecontent, fileMap);

            MultiValueMap<String, Object> uploadbody = new LinkedMultiValueMap<>();
            uploadbody.add("multipartfile", fileentity);
            HttpHeaders uploadheaders = new HttpHeaders();
            uploadheaders.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<MultiValueMap<String, Object>> uploade = new HttpEntity<>(uploadbody, uploadheaders);
            restTemplate.postForEntity("http://localhost:7500/serviceaddfileresource/KEA-Schedule-Teacher", uploade, String.class);
        } catch (IOException e) {
            System.out.println("download.jpg not loaded, failed to upload!");
            e.printStackTrace();
        }
    }

    public void setupScheduleMS() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity;
        JSONObject json;
        json = new JSONObject();
        json.appendField("name", "KEA-Schedule");
        json.appendField("port", 7511);
        json.appendField("enabled", false);
        json.appendField("description", "Display the schedule for teachers and students");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceregistration", HttpMethod.POST, entity, String.class);

        //Add topmenulink eng
        json = new JSONObject();
        json.appendField("path", "/servicepages/KEA-Schedule/index.eng");
        json.appendField("text", "Schedule");
        json.appendField("priority", "10");
        json.appendField("language", "eng");
        json.appendField("description", "");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddtopmenulink/KEA-Schedule", HttpMethod.POST, entity, String.class);
        //Add topmenulink dk
        json = new JSONObject();
        json.appendField("path", "/servicepages/KEA-Schedule/index.dk");
        json.appendField("text", "Skema");
        json.appendField("priority", "10");
        json.appendField("language", "dk");
        json.appendField("description", "");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddtopmenulink/KEA-Schedule", HttpMethod.POST, entity, String.class);

        //Lets upload our print picture
        File file = null;
        try {
            Resource res = rl.getResource("classpath:static/print.png");
            file = res.getFile();
            if(file == null) throw new IOException("File is null");
            byte[] filecontent = Files.readAllBytes(file.toPath());
            MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
            fileMap.add(HttpHeaders.CONTENT_DISPOSITION, "form-data; name=\"multipartfile\"; filename=\"print.png\"");
            fileMap.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(file.toPath()));
            HttpEntity<byte[]> fileentity = new HttpEntity<>(filecontent, fileMap);

            MultiValueMap<String, Object> uploadbody = new LinkedMultiValueMap<>();
            uploadbody.add("multipartfile", fileentity);
            HttpHeaders uploadheaders = new HttpHeaders();
            uploadheaders.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<MultiValueMap<String, Object>> uploade = new HttpEntity<>(uploadbody, uploadheaders);
            restTemplate.postForEntity("http://localhost:7500/serviceaddfileresource/KEA-Schedule", uploade, String.class);
        } catch (IOException e) {
            System.out.println("print.jpg not loaded, failed to upload!");
            e.printStackTrace();
        }

        //Lets upload our print picture
        file = null;
        try {
            Resource res = rl.getResource("classpath:static/download.png");
            file = res.getFile();
            if(file == null) throw new IOException("File is null");
            byte[] filecontent = Files.readAllBytes(file.toPath());
            MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
            fileMap.add(HttpHeaders.CONTENT_DISPOSITION, "form-data; name=\"multipartfile\"; filename=\"download.png\"");
            fileMap.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(file.toPath()));
            HttpEntity<byte[]> fileentity = new HttpEntity<>(filecontent, fileMap);

            MultiValueMap<String, Object> uploadbody = new LinkedMultiValueMap<>();
            uploadbody.add("multipartfile", fileentity);
            HttpHeaders uploadheaders = new HttpHeaders();
            uploadheaders.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<MultiValueMap<String, Object>> uploade = new HttpEntity<>(uploadbody, uploadheaders);
            restTemplate.postForEntity("http://localhost:7500/serviceaddfileresource/KEA-Schedule", uploade, String.class);
        } catch (IOException e) {
            System.out.println("download.jpg not loaded, failed to upload!");
            e.printStackTrace();
        }
    }

    public void setupAdminMS() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity;
        JSONObject json;
        json = new JSONObject();
        json.appendField("name", "KEA-Schedule-admin");
        json.appendField("port", 7510);
        json.appendField("enabled", false);
        json.appendField("description", "admin module");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceregistration", HttpMethod.POST, entity, String.class);

        //Add topmenulink eng
        json = new JSONObject();
        json.appendField("path", "/servicepages/KEA-Schedule-Admin/index.eng");
        json.appendField("text", "Course admin");
        json.appendField("priority", "100");
        json.appendField("language", "eng");
        json.appendField("description", "");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddtopmenulink/KEA-Schedule-admin", HttpMethod.POST, entity, String.class);
        //Add topmenulink dk
        json = new JSONObject();
        json.appendField("path", "/servicepages/KEA-Schedule-Admin/index.dk");
        json.appendField("text", "Kursus admin");
        json.appendField("priority", "100");
        json.appendField("language", "dk");
        json.appendField("description", "");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddtopmenulink/KEA-Schedule-admin", HttpMethod.POST, entity, String.class);

        //Action: GroupService
        json = new JSONObject();
        json.appendField("callbackurl", "http://localhost:" + serviceport + "/actions/group/create/");
        json.appendField("actionname", "GroupService.create");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddaction/KEA-Schedule-admin", HttpMethod.POST, entity, String.class);
        json = new JSONObject();
        json.appendField("callbackurl", "http://localhost:" + serviceport + "/actions/group/edit/");
        json.appendField("actionname", "GroupService.edit");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddaction/KEA-Schedule-admin", HttpMethod.POST, entity, String.class);
        json = new JSONObject();
        json.appendField("callbackurl", "http://localhost:" + serviceport + "/actions/group/delete/");
        json.appendField("actionname", "GroupService.delete");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddaction/KEA-Schedule-admin", HttpMethod.POST, entity, String.class);

        //Action: UserService
        json = new JSONObject();
        json.appendField("callbackurl", "http://localhost:" + serviceport + "/actions/user/create/");
        json.appendField("actionname", "UserService.create");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddaction/KEA-Schedule-admin", HttpMethod.POST, entity, String.class);
        json = new JSONObject();
        json.appendField("callbackurl", "http://localhost:" + serviceport + "/actions/user/edit/");
        json.appendField("actionname", "UserService.edit");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddaction/KEA-Schedule-admin", HttpMethod.POST, entity, String.class);
        json = new JSONObject();
        json.appendField("callbackurl", "http://localhost:" + serviceport + "/actions/user/delete/");
        json.appendField("actionname", "UserService.delete");
        entity = new HttpEntity<JSONObject>(json, headers);
        restTemplate.exchange("http://localhost:" + infrastructureport + "/serviceaddaction/KEA-Schedule-admin", HttpMethod.POST, entity, String.class);
    }


}