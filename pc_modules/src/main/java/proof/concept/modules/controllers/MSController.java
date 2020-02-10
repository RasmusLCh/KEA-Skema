package proof.concept.modules.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
@RequestMapping("/service/")
public class MSController {
    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("Test")
    @ResponseBody
    public String get_test() throws IOException {
        Resource resource = resourceLoader.getResource("http://localhost:10000/b.html");
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"));
        String s= "";
        while(br.ready()){
            //System.out.println(br.readLine());
            s+=br.readLine();
        }
        return s;
    }
}
