package proof.concept.microservice1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages/")
public class pagecontroller {
    @GetMapping("test")
    public String get_test(){
        return "index";
    }
}
