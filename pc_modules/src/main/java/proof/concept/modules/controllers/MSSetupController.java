package proof.concept.modules.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import proof.concept.modules.modules.MicroService;
import proof.concept.modules.modules.MicroServiceAction;
import proof.concept.modules.modules.MicroServicePageInjection;
import proof.concept.modules.services.MSSetupService;

@Controller
@RequestMapping("/")
public class MSSetupController {
    private ResourceLoader resourceLoader;

    MSSetupService msss;

    @Autowired
    public MSSetupController(MSSetupService msss){
        this.msss = msss;
    }

    @PostMapping("serviceregistration")
    public void post_serviceregistration(MicroService ms){
        msss.serviceregistration(ms);
    }

    @PostMapping("serviceremoval")
    public void post_serviceremoval(int msservice_id){
        msss.serviceremoval(msservice_id);
    }

    @PostMapping("serviceaddpageinjection")
    public void post_serviceaddpageinjection(MicroServicePageInjection mspi){
        msss.serviceaddpageinjection(mspi);
    }

    @PostMapping("serviceaddaction")
    public void post_serviceaddaction(MicroServiceAction msa){
        msss.serviceaddaction(msa);
    }
}
