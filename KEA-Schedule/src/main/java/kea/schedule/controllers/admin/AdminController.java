package kea.schedule.controllers.admin;

import kea.schedule.moduls.MicroService;
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
    MicroServiceService msservice;

    @Autowired
    public AdminController(MicroServiceService msservice){
        this.msservice = msservice;
    }

    @GetMapping({"", "index"})
    public String get_index(){
        return "admin/users/index";
    }

    @GetMapping({"groups/", "groups/index"})
    public String get_groups(){
        return "admin/groups/index";
    }

    @GetMapping({"courses/", "courses/index"})
    public String get_courses(){
        return "admin/courses/index";
    }

    @GetMapping({"statistics/", "statistics/index"})
    public String get_statistics(){
        return "admin/statistics/index";
    }





    @GetMapping({"services/", "services/index"})
    public String get_services(Model model){
        List<MicroService> microservices = msservice.findAll();
        System.out.println("services size = " + microservices.size());
        model.addAttribute("microservices", microservices);

        return "admin/services/index";
    }

    @GetMapping({"actions/", "actions/index"})
    public String get_actions(){
        return "admin/actions/index";
    }

    @GetMapping({"topmenulinks/", "topmenulinks/index"})
    public String get_topmenulinks(){
        return "admin/topmenulinks/index";
    }

    /*
    * ******* pageinjections: start
    * */
/*
    @GetMapping({"pageinjections/", "pageinjections/index"})
    public String get_pageinjections(Model model) {
        List<MicroService> microservices = msservice.findAll();
        System.out.println("services size = " + microservices.size());
        model.addAttribute("microservices", microservices);
        return "admin/pageinjections/index";
    }


 */
    /*
     * ******* pageinjections: end
     * */
}
