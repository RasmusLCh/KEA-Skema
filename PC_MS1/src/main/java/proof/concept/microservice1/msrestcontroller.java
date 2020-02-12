package proof.concept.microservice1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rest/")
public class msrestcontroller {
    @GetMapping("getcolor")
    public ResponseEntity<MyColor> get_getcolor(){
        return new ResponseEntity<>(new MyColor(Math.random() * 255, Math.random() * 255, Math.random() * 255), HttpStatus.OK);
    }
}
