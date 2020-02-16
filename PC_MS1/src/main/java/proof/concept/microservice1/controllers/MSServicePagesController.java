package proof.concept.microservice1.controllers;

import net.minidev.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * If our program calls for a page in the microservice, this controller handles it.
 * */
@Controller
@RequestMapping("/servicepages/MS1/")
public class MSServicePagesController {
    @GetMapping({"test"})
    public String get_test(){
        return "test_eng";
    }

    @GetMapping("test.dk")
    public String get_action_dk(){
        System.out.println("dk");
        return "test_dk";
    }

    @GetMapping("test.eng")
    public String get_action_eng(){
        System.out.println("uh");
        return "test_eng";
    }

    @GetMapping("action")
    public String get_action(){
        System.out.println("action");
        return "test_eng";
    }



    @PostMapping("action")
    public ResponseEntity post_action(@RequestBody JSONObject json, HttpServletRequest hsr){

        for (Enumeration e = hsr.getParameterNames() ; e.hasMoreElements() ;) {
            System.out.println(e.nextElement());

        }
        System.out.println("JSON: " + json.toJSONString());


        return new ResponseEntity<>(HttpStatus.OK);
    }


}
