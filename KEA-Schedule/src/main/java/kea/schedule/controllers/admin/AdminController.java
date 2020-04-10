package kea.schedule.controllers.admin;

import kea.schedule.moduls.MicroService;
import kea.schedule.services.AuthenticationService;
import kea.schedule.services.MicroServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/")
public class AdminController {
    private MicroServiceService msservice;
    private AuthenticationService authservice;
    @Autowired
    public AdminController(MicroServiceService msservice, AuthenticationService authservice){
        this.msservice = msservice;
        this.authservice = authservice;
    }

    @GetMapping({"", "index"})
    public String get_index(){
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        return "admin/index";
    }

    @GetMapping({"courses/", "courses/index"})
    public String get_courses(){
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        return "admin/courses/index";
    }

    @GetMapping({"statistics/", "statistics/index"})
    public String get_statistics(){
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        return "admin/statistics/index";
    }
}
