package proof.concept.modules.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import proof.concept.modules.modules.MicroService;
import proof.concept.modules.modules.Action;
import proof.concept.modules.modules.PageInjection;
import proof.concept.modules.modules.TopMenuLink;
import proof.concept.modules.services.MSSetupService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class MSSetupController {
    private ResourceLoader resourceLoader;
    private MSSetupService msss;

    //Defined in application.properties file
    @Value("${server.port.service:7500}")
    int serviceport;

    @Autowired
    public MSSetupController(MSSetupService msss){
        this.msss = msss;
    }

    @PostMapping("serviceregistration")
    public String post_serviceregistration(MicroService ms, Model model, HttpServletRequest hsr){
        //Check that we are using the service port
        if(hsr.getLocalPort() == serviceport){
            ms = msss.serviceregistration(ms);
            model.addAttribute("microservice", ms);
            model.addAttribute("microservices", msss.getAllMS());
            return "setup/serviceregistration";
        }
        return "error";
    }

    @PostMapping("serviceremoval")
    public String post_serviceremoval(MicroService ms, Model model, HttpServletRequest hsr){
        if(hsr.getLocalPort() == serviceport){
            msss.serviceremoval(ms);
            model.addAttribute("microservice", ms);
            model.addAttribute("microservices", msss.getAllMS());
            return "setup/serviceremoval";
        }
        return "error";
    }

    @PostMapping("serviceaddpageinjection")
    public String post_serviceaddpageinjection(PageInjection mspi, HttpServletRequest hsr){
        if(hsr.getLocalPort() == serviceport){
            msss.serviceaddpageinjection(mspi);
            return "setup/serviceaddpageinjection";
        }
        return "error";
    }

    @PostMapping("serviceaddaction")
    public String post_serviceaddaction(Action msa, HttpServletRequest hsr){
        if(hsr.getLocalPort() == serviceport){
            msss.serviceaddaction(msa);
            return "setup/serviceaddaction";
        }
        return "error";
    }

    @PostMapping("serviceaddtopmenulink")
    public String post_serviceaddtopmenulink(TopMenuLink tml, HttpServletRequest hsr){
        if(hsr.getLocalPort() == serviceport){
            return "setup/serviceaddaction";
        }
        return "error";
    }
}
