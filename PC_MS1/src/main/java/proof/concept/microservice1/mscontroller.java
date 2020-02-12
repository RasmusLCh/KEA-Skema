package proof.concept.microservice1;

import net.minidev.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/")
public class mscontroller {
    @GetMapping({"", "index"})
    public String get_root(){
        return "index.html";
    }

    @GetMapping({"setup"})
    public String get_setup(){
        return "setup.html";
    }

    @PostMapping("setupall")
    public ResponseEntity post_action() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity;
        JSONObject json;
        //Setup the service
        json = new JSONObject();
        json.appendField("name", "MS1");
        json.appendField("port", 10001);
        json.appendField("enabled", false);
        entity = new HttpEntity<JSONObject>(json,headers);
        restTemplate.exchange("http://localhost:10000/serviceregistration", HttpMethod.POST, entity, String.class);
        //Add topmenulink
        json = new JSONObject();
        json.appendField("path", "localhost:10000/service/MS1/test");
        json.appendField("text", "MS1");
        json.appendField("description", "This is my description");
        entity = new HttpEntity<JSONObject>(json,headers);
        restTemplate.exchange("http://localhost:10000/serviceaddtopmenulink/MS1", HttpMethod.POST, entity, String.class);
        //Add action
        json = new JSONObject();
        json.appendField("callbackurl", "http://localhost:10001/pages/action");
        json.appendField("actionname", "cron");
        entity = new HttpEntity<JSONObject>(json,headers);
        restTemplate.exchange("http://localhost:10000/serviceaddaction/MS1", HttpMethod.POST, entity, String.class);
        //Add pageinjection CSS
        json = new JSONObject();
        json.appendField("type", "CSS");
        json.appendField("page", "/index.html");
        json.appendField("data", "h1 { color: green; }");
        json.appendField("priority", 5);
        entity = new HttpEntity<JSONObject>(json,headers);
        restTemplate.exchange("http://localhost:10000/serviceaddpageinjection/MS1", HttpMethod.POST, entity, String.class);
        //Add page injection JS
        json = new JSONObject();
        json.appendField("type", "JS");
        json.appendField("page", "/index.html");
        json.appendField("data", "$( document ).ready(function() { " +
                " $( '#item' ).prepend('Hello there, what are you?') ; " +
                "});");
        json.appendField("priority", 5);
        entity = new HttpEntity<JSONObject>(json,headers);
        restTemplate.exchange("http://localhost:10000/serviceaddpageinjection/MS1", HttpMethod.POST, entity, String.class);
        //Add page injection JS - Lets go funky - Not in manueal!!
        json = new JSONObject();
        json.appendField("type", "JS");
        json.appendField("page", "/index.html");
        json.appendField("data", "setInterval(function(){ " +
                " $.ajax({" +
                    " method: 'GET', " +
                    " url: '/servicerest/MS1/getcolor'," +
                    " contentType: 'JSON', " +
                    " dataType: 'JSON' " +
                " }).done(function (data) {" +
                    " $('p').css('color', 'rgb('+data.red+', '+data.green+', '+data.blue+')');" +
                "});" +
                "}, 2000);");
        json.appendField("priority", 5);
        entity = new HttpEntity<JSONObject>(json,headers);
        restTemplate.exchange("http://localhost:10000/serviceaddpageinjection/MS1", HttpMethod.POST, entity, String.class);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
