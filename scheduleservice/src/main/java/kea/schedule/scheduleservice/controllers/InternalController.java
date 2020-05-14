package kea.schedule.scheduleservice.controllers;

import kea.schedule.scheduleservice.models.ModelInterface;
import kea.schedule.scheduleservice.services.CRUDServiceInterface;
import kea.schedule.scheduleservice.services.ModelService;
import kea.schedule.scheduleservice.services.SetupService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Internal controller for schedule-service, used for setting up the service in the infrastructure
 * */

@Controller
@RequestMapping("/")
public class InternalController {
    @Value("${ms.port.service:7510}")
    int serviceport;

    private SetupService setupservice;
    private ModelService modelservice;
    public InternalController(SetupService setupservice, ModelService modelservice){
        this.setupservice = setupservice;
        this.modelservice = modelservice;
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
            setupservice.install();
            return "index";
        }
        return "forbidden";
    }

    @PostMapping("setup")
    public String post_setup(HttpServletRequest hsr){
        if(hsr.getLocalPort() == serviceport){
            setupservice.setupAdminMS();
            setupservice.setupScheduleMS();
            setupservice.setupTeacherMS();
            return "index";
        }
        return "forbidden";
    }

    @GetMapping("find/ById/{classname}/{id}")
    public ResponseEntity<ModelInterface> get_byid(@PathVariable(name="classname", required = true) String classname, @PathVariable(name="id", required = true) int id, HttpServletRequest hsr){
        if(hsr.getLocalPort() == serviceport){
            CRUDServiceInterface service = modelservice.getService(classname);
            if(service != null){
                ModelInterface model = service.findById(id);
                return new ResponseEntity<>(model, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("find/all/{classname}/")
    public ResponseEntity<List> get_all(@PathVariable(name="classname", required = true) String classname, HttpServletRequest hsr){
        if(hsr.getLocalPort() == serviceport){
            CRUDServiceInterface service = modelservice.getService(classname);
            if(service != null){
                List list = service.findAll();
                if(list == null){
                    list = new ArrayList();
                }
                return new ResponseEntity<>(list, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
