package kea.schedule.controllers;

import kea.schedule.modules.MicroService;
import kea.schedule.modules.TopMenuLink;
import kea.schedule.services.MSService;
import kea.schedule.services.TopMenuLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Controller
@RequestMapping("/servicepages/")
public class MSController {
    private MSService mss;
    private TopMenuLinkService menuservice;
    private ResourceLoader rl;

    @Autowired
    public MSController(MSService mss, TopMenuLinkService menuservice, ResourceLoader rl){
        this.mss = mss;
        this.menuservice = menuservice;
        this.rl = rl;
    }

    @GetMapping("{servicename}/{page}")
    public String get_servicename_page(@PathVariable String servicename, @PathVariable String page, Model model) throws IOException {
        MicroService ms = mss.findMSByName(servicename);
        if(ms != null){
            System.out.println("Getting: " + "http://localhost:"+ms.getPort()+"/servicepages/"+servicename+"/"+page);
            Resource resource = rl.getResource("http://localhost:"+ms.getPort()+"/servicepages/"+servicename+"/"+page);
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"));
            String s= "";
            while(br.ready()){
                //System.out.println(br.readLine());
                s+=br.readLine();
            }
            model.addAttribute("data", s);
        }
        else{
            model.addAttribute("data", "Unknown error");
        }
        return "servicedata";
    }

    @ModelAttribute("topmenu")
    public List<TopMenuLink> modelattribute_topmenu(){
        return menuservice.findAll();
    }
}
