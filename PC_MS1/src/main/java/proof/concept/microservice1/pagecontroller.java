package proof.concept.microservice1;

import net.minidev.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Controller
@RequestMapping("/pages/")
public class pagecontroller {
    @GetMapping("test")
    public String get_test(){
        return "test";
    }

    @GetMapping("action")
    public String get_action(){
        System.out.println("action");
        return "test";
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
