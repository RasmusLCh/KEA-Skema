package proof.concept.microservice1.controllers;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import proof.concept.microservice1.models.MyColor;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * If our program calls for a REST page in the microservice, this controller handles it.
 * */
@Controller
@RequestMapping("/servicerest/MS1/")
public class MSRestController {
    @GetMapping("getcolor")
    public ResponseEntity<MyColor> get_getcolor(@RequestParam(value="user_id", required=false, defaultValue = "0") int user_id){
        return new ResponseEntity<>(new MyColor(Math.random() * 255, Math.random() * 255, Math.random() * 255), HttpStatus.OK);
    }

    @PostMapping("restaction")
    public ResponseEntity post_action(@RequestBody JSONObject json, HttpServletRequest hsr){

        for (Enumeration e = hsr.getParameterNames(); e.hasMoreElements() ;) {
            System.out.println(e.nextElement());

        }
        System.out.println("restaction JSON: " + json.toJSONString());


        return new ResponseEntity<>(HttpStatus.OK);
    }
}
