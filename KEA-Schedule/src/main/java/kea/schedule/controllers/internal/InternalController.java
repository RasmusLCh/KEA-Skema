package kea.schedule.controllers.internal;

import kea.schedule.moduls.*;
import kea.schedule.services.CRUDServiceInterface;
import kea.schedule.services.MSSetupService;
import kea.schedule.services.ModelService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Only requests made on the service port are valid to the controller
 * */
@Controller
@CrossOrigin(origins = "*") // A better solution would be to be more narrow in who can access..
@RequestMapping("/")
public class InternalController {
    private ResourceLoader resourceLoader;
    private MSSetupService msss;
    private ModelService modelservice;
    //Defined in application.properties file, this is the port our MicroServices needs to access.
    @Value("${server.port.service:7500}")
    int serviceport;

    @Autowired
    public InternalController(MSSetupService msss, ModelService modelservice){
        this.msss = msss;
        this.modelservice= modelservice;
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
                mspi.setMicroservice(ms);
                msss.serviceaddpageinjection(mspi);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("serviceaddaction/{servicename}")
    public ResponseEntity post_serviceaddaction(@PathVariable(name = "servicename") String servicename, @RequestBody Action action, HttpServletRequest hsr){
        if(hsr.getLocalPort() == serviceport){
            MicroService ms = msss.findByName(servicename);
            if(ms != null && action.getCallbackurl() != null && action.getActionname() != null){
                action.setMicroservice(ms);
                msss.serviceaddaction(action);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("serviceaddtopmenulink/{servicename}")
    public ResponseEntity post_serviceaddtopmenulink(@PathVariable(name = "servicename") String servicename, @RequestBody TopMenuLink tml, HttpServletRequest hsr){
        if(hsr.getLocalPort() == serviceport){
            MicroService ms = msss.findByName(servicename);
            if(ms != null && tml.getPath() != null && tml.getText() != null){
                tml.setMicroservice(ms);
                msss.serviceaddtopmenulink(tml);
                System.out.println("Menu link added!");
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                System.out.println(servicename + " + " + ms + " - " + tml.getPath() + " * " + tml.getText());
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    @PostMapping("serviceaddmicroserviceoption/{servicename}")
    public ResponseEntity post_serviceaddmicroserviceoption(@PathVariable(name = "servicename") String servicename, @RequestBody MicroServiceOption microserviceoption, HttpServletRequest hsr){
        System.out.println("serviceaddmicroserviceoption");
        if(hsr.getLocalPort() == serviceport){
            MicroService ms = msss.findByName(servicename);
            if(ms != null && microserviceoption.getVariableName() != null && !microserviceoption.getVariableName().equals("")){
                microserviceoption.setMicroservice(ms);
                msss.serviceaddmicroserviceoption(microserviceoption);
                System.out.println("Menu link added!");
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("serviceexists/{servicename}")
    public ResponseEntity<MicroService> get_serviceexists(@PathVariable(name = "servicename") String servicename){
        System.out.println("serviceexists");
        MicroService ms = msss.findByName(servicename);
        if(ms == null){
            ms = new MicroService();
        }
        return new ResponseEntity<>(ms, HttpStatus.OK);
    }

    @PostMapping("serviceaddfileresource/{servicename}")
    public ResponseEntity post_addfileresource(@PathVariable(name = "servicename") String servicename, @RequestParam("multipartfile") MultipartFile multipartfile, HttpServletRequest hsr){
        if(hsr.getLocalPort() == serviceport){
            MicroService ms = msss.findByName(servicename);
            if(ms != null && multipartfile != null && !multipartfile.isEmpty() && multipartfile.getOriginalFilename() != null && multipartfile.getOriginalFilename() != ""){
                msss.serviceaddfileresource(ms, multipartfile);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            System.out.println(ms );
            System.out.println(multipartfile);
            System.out.println(multipartfile.isEmpty());
            System.out.println(multipartfile.getOriginalFilename());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("find/all/{classname}/")
    public ResponseEntity<JSONObject> get_all(@PathVariable(name="classname", required = true) String classname, HttpServletRequest hsr){
        if(hsr.getLocalPort() == serviceport){
            CRUDServiceInterface service = modelservice.getService(classname);
            List list = service.findAll();
            JSONObject json = new JSONObject();
            for(Object mi : list){
                ModelInterface minter = (ModelInterface)mi;
                json.appendField(Integer.toString(minter.getId()), minter.toJSON(new JSONObject()));
            }
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("find/ById/{classname}/{id}")
    public ResponseEntity<JSONObject> get_byid(@PathVariable(name="classname", required = true) String classname, @PathVariable(name="id", required = true) int id, HttpServletRequest hsr){
        if(hsr.getLocalPort() == serviceport){
            CRUDServiceInterface service = modelservice.getService(classname);
            ModelInterface model = service.findById(id);
            JSONObject json = new JSONObject();
            json = model.toJSON(json);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
