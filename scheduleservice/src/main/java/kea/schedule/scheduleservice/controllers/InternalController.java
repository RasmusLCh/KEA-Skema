package kea.schedule.scheduleservice.controllers;

import kea.schedule.scheduleservice.services.SetupService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class InternalController {
    @Value("${ms.port.service:7510}")
    int serviceport;

    private SetupService setupservice;

    public InternalController(SetupService setupservice){
        this.setupservice = setupservice;
    }

    @GetMapping({"", "/", "index"})
    public String get_root(HttpServletRequest hsr){
        if(hsr.getLocalPort() == serviceport){
            return "index";
        }
        return "forbidden";
    }

    @PostMapping("install")
    public String post_install(HttpServletRequest hsr){
        if(hsr.getLocalPort() == serviceport){
            setupservice.setup();
            return "index";
        }
        return "forbidden";
    }


}
