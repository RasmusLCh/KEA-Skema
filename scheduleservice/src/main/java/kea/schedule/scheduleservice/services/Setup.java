package kea.schedule.scheduleservice.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import net.minidev.json.JSONObject;

@Service
public class Setup {
    public void setup(){

    }

    public void setup_admin(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity;
        JSONObject json;

        json = new JSONObject();
        json.appendField("name", "MS1");
        json.appendField("port", 10001);
        json.appendField("enabled", false);
        json.appendField("description", "this is a description of the MS1 service...");
        entity = new HttpEntity<JSONObject>(json,headers);
        restTemplate.exchange("http://localhost:7500/serviceregistration", HttpMethod.POST, entity, String.class);
    }

    public void setup_teacher(){

    }

    public void setup_student(){

    }
}
