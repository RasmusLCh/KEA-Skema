package proof.concept.microservice1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class mscontroller {
    @GetMapping({"", "index"})
    public String get_root(){
        return "index.html";
    }

    @GetMapping({"setup"})
    public String get_setup(){
        return "setup.html";
    }
}
