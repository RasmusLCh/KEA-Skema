package proof.concept.microservice1.controllers;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


/**
 * This controller is used for internal communication, mainly to setup the microservice.
 * */
@Controller
@RequestMapping("/")
public class MSSetupController {
    private ResourceLoader rl;

    @Autowired
    public MSSetupController(ResourceLoader rl){
        this.rl = rl;
    }

    @GetMapping({"", "index"})
    public String get_root(){
        return "index.html";
    }

    @GetMapping({"setup"})
    public String get_setup(){
        return "setup.html";
    }

    /**
     * Running this method, will register our microservice in our program
     * */
    @PostMapping("setupall")
    public ResponseEntity post_action() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity;
        JSONObject json;
        //Setup the service
        System.out.println("Add the service");
        json = new JSONObject();
        json.appendField("name", "MS1");
        json.appendField("port", 10001);
        json.appendField("enabled", false);
        json.appendField("description", "this is a description of the MS1 service...");
        entity = new HttpEntity<JSONObject>(json,headers);
        restTemplate.exchange("http://localhost:7500/serviceregistration", HttpMethod.POST, entity, String.class);
        //Add topmenulink
        json = new JSONObject();
        json.appendField("path", "https://localhost/servicepages/MS1/test.eng");
        json.appendField("text", "MS1_ENG");
        json.appendField("language", "eng");
        json.appendField("description", "This is my description");
        entity = new HttpEntity<JSONObject>(json,headers);
        restTemplate.exchange("http://localhost:7500/serviceaddtopmenulink/MS1", HttpMethod.POST, entity, String.class);
        //Add topmenulink
        json = new JSONObject();
        json.appendField("path", "https://localhost/servicepages/MS1/test.dk");
        json.appendField("text", "MS1_DK");
        json.appendField("language", "dk");
        json.appendField("description", "This is my description");
        entity = new HttpEntity<JSONObject>(json,headers);
        restTemplate.exchange("http://localhost:7500/serviceaddtopmenulink/MS1", HttpMethod.POST, entity, String.class);
        //Add action
        json = new JSONObject();
        json.appendField("callbackurl", "http://localhost:10001/servicepages/MS1/action");
        json.appendField("actionname", "cron");
        entity = new HttpEntity<JSONObject>(json,headers);
        restTemplate.exchange("http://localhost:7500/serviceaddaction/MS1", HttpMethod.POST, entity, String.class);
        //Add pageinjection CSS
        json = new JSONObject();
        json.appendField("type", "CSS");
        json.appendField("page", "/index.eng");
        json.appendField("data", "h1 { color: green; }");
        json.appendField("priority", 5);
        entity = new HttpEntity<JSONObject>(json,headers);
        restTemplate.exchange("http://localhost:7500/serviceaddpageinjection/MS1", HttpMethod.POST, entity, String.class);
        //Add page injection JS
        json = new JSONObject();
        json.appendField("type", "JS");
        json.appendField("page", "/index.eng");
        json.appendField("data", "$( document ).ready(function() { " +
                " $( '#item' ).prepend('Hello there, what are you?') ; " +
                "});");
        json.appendField("priority", 5);
        entity = new HttpEntity<JSONObject>(json,headers);
        restTemplate.exchange("http://localhost:7500/serviceaddpageinjection/MS1", HttpMethod.POST, entity, String.class);
        //Add page injection JS - Lets go funky - Not in manueal!!
        json = new JSONObject();
        json.appendField("type", "JS");
        json.appendField("page", "/index.eng");
        json.appendField("data", "setInterval(function(){ " +
                " $.ajax({" +
                    " method: 'GET', " +
                    " url: '/servicerest/MS1/getcolor'," +
                    " contentType: 'application/json', " +
                    " dataType: 'json' " +
                " }).done(function (data) {" +
                    " $('p').css('color', 'rgb('+data.red+', '+data.green+', '+data.blue+')');" +
                "});" +
                "}, 2000);");
        json.appendField("priority", 5);
        entity = new HttpEntity<JSONObject>(json,headers);
        restTemplate.exchange("http://localhost:7500/serviceaddpageinjection/MS1", HttpMethod.POST, entity, String.class);
        //Add page injection JS - Lets go funky - Not in manueal!!
        json = new JSONObject();
        json.appendField("type", "JS");
        json.appendField("page", "/index.eng");
        json.appendField("data", "setInterval(function(){ " +
                " $.ajax({" +
                " method: 'POST', " +
                " url: '/servicerest/MS1/restaction'," +
                " contentType: 'application/json', " +
                " dataType: 'JSON', " +
                " data: JSON.stringify({ " +
                " name: 'MS1', " +
                " port: 10001, " +
                " enabled: false " +
                " }) " +
                " }).done(function (data) {" +
                " $('p').css('color', 'rgb('+data.red+', '+data.green+', '+data.blue+')');" +
                "});" +
                "}, 2000);");
        json.appendField("priority", 5);
        entity = new HttpEntity<JSONObject>(json,headers);
        restTemplate.exchange("http://localhost:7500/serviceaddpageinjection/MS1", HttpMethod.POST, entity, String.class);


        //Add microserviceoption

        json = new JSONObject();
        json.appendField("variableName", "ms1_test");
        json.appendField("description", "This is the description");
        entity = new HttpEntity<JSONObject>(json,headers);
        restTemplate.exchange("http://localhost:7500/serviceaddmicroserviceoption/MS1", HttpMethod.POST, entity, String.class);

        //Lets upload our smiley file
        File file = null;
        try {
            Resource res = rl.getResource("classpath:static/smiley.jpg");
            file = res.getFile();
            if(file == null) throw new IOException("File is null");
            System.out.println((file.toPath().toString()));
            byte[] filecontent = Files.readAllBytes(file.toPath());
            MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
            fileMap.add(HttpHeaders.CONTENT_DISPOSITION, "form-data; name=\"multipartfile\"; filename=\"smiley.jpg\"");
            fileMap.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(file.toPath()));
            HttpEntity<byte[]> fileentity = new HttpEntity<>(filecontent, fileMap);
            System.out.println("Filename" + file.getName());
            //uploadheaders.add("filename", "smiley.jpg");
            //uploadheaders.add("Content-Type", Files.probeContentType(file.toPath()));


            MultiValueMap<String, Object> uploadbody = new LinkedMultiValueMap<>();
            uploadbody.add("multipartfile", fileentity);
            HttpHeaders uploadheaders = new HttpHeaders();
            uploadheaders.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<MultiValueMap<String, Object>> uploade = new HttpEntity<>(uploadbody, uploadheaders);
            restTemplate.postForEntity("http://localhost:7500/serviceaddfileresource/MS1", uploade, String.class);
        } catch (IOException e) {
            System.out.println("smiley.jpg not loaded, failed to upload!");
            e.printStackTrace();
        }



        return new ResponseEntity<>(HttpStatus.OK);
    }
}
