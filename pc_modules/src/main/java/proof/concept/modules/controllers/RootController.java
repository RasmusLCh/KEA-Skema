package proof.concept.modules.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;

@Controller
@RequestMapping("/")
public class RootController {
    @Autowired
    private ResourceLoader resourceLoader;
    @GetMapping({"index.html", ""})
    public String get_index(Model model) throws IOException {
        Resource resource = resourceLoader.getResource("http://localhost:10000/b.html");
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"));
        while(br.ready()){
            System.out.println(br.readLine());
        }
        return "index.html";
    }

    @GetMapping({"b.html"})
    public String get_b(Model model) throws IOException {
        System.out.println("b");
        return "index.html";
    }
}
