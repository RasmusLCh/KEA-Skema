package microservice.infrastructure.controllers;

import microservice.infrastructure.models.User;
import microservice.infrastructure.models.UserMicroServiceOption;
import microservice.infrastructure.services.UserMicroServiceOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/usermicroserviceoption/")
public class UserMicroServiceOptionRestController {
    @Autowired
    private HttpSession session;
    private UserMicroServiceOptionService usermicroseroptionservice;

    public UserMicroServiceOptionRestController(UserMicroServiceOptionService usermicroseroptionservice){
        this.usermicroseroptionservice = usermicroseroptionservice;
    }

    @PostMapping(value = "set", consumes = "application/json")
    public ResponseEntity<String> set(@RequestBody UserMicroServiceOption umso){
        if(session.getAttribute("curuser") != null && session.getAttribute("curuser") instanceof User){
            umso.setUser((User)session.getAttribute("curuser"));
            System.out.println("Received " + umso.getMicroserviceoption() + " " + umso.getActive() + " " + umso.getUser());
            //Edit will also create the element if its not already created
            usermicroseroptionservice.edit(umso);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            System.out.println("No user!");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
