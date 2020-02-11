package proof.concept.modules.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import proof.concept.modules.modules.MicroService;
import proof.concept.modules.services.MSService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
@RequestMapping("/service/")
public class MSController {
    private MSService mss;
    private ResourceLoader rl;

    @Autowired
    public MSController(MSService mss, ResourceLoader rl){
        this.mss = mss;
        this.rl = rl;
    }

    @GetMapping("{servicename}/{page}")
    @ResponseBody
    public String get_servicename_page(@PathVariable String servicename, @PathVariable String page) throws IOException {
        MicroService ms = mss.findMSByName(servicename);
        if(ms != null){
            System.out.println("Getting: " + "http://localhost:"+ms.getPort()+"/pages/"+page);
            Resource resource = rl.getResource("http://localhost:"+ms.getPort()+"/pages/"+page);
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"));
            String s= "";
            while(br.ready()){
                //System.out.println(br.readLine());
                s+=br.readLine();
            }
            return s;
        }
        System.out.println("Service: " + servicename);
        return "Unknown service";
    }
}
