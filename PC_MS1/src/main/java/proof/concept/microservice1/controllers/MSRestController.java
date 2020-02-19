package proof.concept.microservice1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import proof.concept.microservice1.models.MyColor;

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
}
