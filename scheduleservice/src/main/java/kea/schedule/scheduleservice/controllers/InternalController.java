package kea.schedule.scheduleservice.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class InternalController {
    @Value("${ms.port.service:7510}")
    int serviceport;

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
            return "index";
        }
        return "forbidden";
    }
}
