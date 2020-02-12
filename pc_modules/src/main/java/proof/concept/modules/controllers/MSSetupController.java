package proof.concept.modules.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import proof.concept.modules.modules.MicroService;
import proof.concept.modules.modules.Action;
import proof.concept.modules.modules.PageInjection;
import proof.concept.modules.modules.TopMenuLink;
import proof.concept.modules.services.MSSetupService;

import javax.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin(origins = "*") // A better solution would be to be more narrow in who can access..
@RequestMapping("/")
public class MSSetupController {
    private ResourceLoader resourceLoader;
    private MSSetupService msss;

    //Defined in application.properties file, this is the port our MicroServices needs to access.
    @Value("${server.port.service:7500}")
    int serviceport;

    @Autowired
    public MSSetupController(MSSetupService msss){
        this.msss = msss;
    }

    @PostMapping(value = "serviceregistration", consumes = "application/json")
    public ResponseEntity post_serviceregistration(@RequestBody MicroService ms, HttpServletRequest hsr){
        System.out.println("post_serviceregistration");
        //Check that we are using the service port
        if(hsr.getLocalPort() == serviceport){
            msss.serviceregistration(ms);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("serviceremoval")
    public ResponseEntity post_serviceremoval(@RequestBody MicroService ms, HttpServletRequest hsr){
        if(hsr.getLocalPort() == serviceport){
            msss.serviceremoval(ms);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("serviceaddpageinjection/{servicename}")
    public ResponseEntity post_serviceaddpageinjection(@PathVariable(name = "servicename") String servicename, @RequestBody PageInjection mspi, HttpServletRequest hsr){
        if(hsr.getLocalPort() == serviceport){
            MicroService ms = msss.findByName(servicename);
            if(ms != null && mspi.getPage() != null && mspi.getType() != null && mspi.getData() != null) {
                System.out.println("serviceaddpageinjection");
                mspi.setMicroservice_id(ms.getId());
                msss.serviceaddpageinjection(mspi);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("serviceaddaction/{servicename}")
    public ResponseEntity post_serviceaddaction(@PathVariable(name = "servicename") String servicename, @RequestBody Action action, HttpServletRequest hsr){
        if(hsr.getLocalPort() == serviceport){
            MicroService ms = msss.findByName(servicename);
            if(ms != null && action.getCallbackurl() != null && action.getActionname() != null){
                action.setMicroservice_id(ms.getId());
                msss.serviceaddaction(action);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("serviceaddtopmenulink/{servicename}")
    public ResponseEntity post_serviceaddtopmenulink(@PathVariable(name = "servicename") String servicename, @RequestBody TopMenuLink tml, HttpServletRequest hsr){
        if(hsr.getLocalPort() == serviceport){
            MicroService ms = msss.findByName(servicename);
            if(ms != null && tml.getPath() != null && tml.getText() != null){
                tml.setMicroservice_id(ms.getId());
                msss.serviceaddtopmenulink(tml);
                System.out.println("Menu link added!");
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                System.out.println(servicename + " + " + ms + " - " + tml.getPath() + " * " + tml.getText());
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
