package proof.concept.architecture.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class AdminController {
    @GetMapping({"", "index", "users/", "users/index"})
    public String get_index(){
        return "admin/users/index";
    }

    @GetMapping({"services/", "services/index"})
    public String get_services(){
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

    @GetMapping({"pageinjections/", "pageinjections/index"})
    public String get_pageinjections(){
        return "admin/pageinjections/index";
    }
}
