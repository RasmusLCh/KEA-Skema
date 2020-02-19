package kea.schedule.controllers;

import kea.schedule.moduls.MicroService;
import kea.schedule.moduls.TopMenuLink;
import kea.schedule.moduls.User;
import kea.schedule.services.LangService;
import kea.schedule.services.MicroServiceService;
import kea.schedule.services.TopMenuLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Controller
@RequestMapping("/servicepages/")
public class MSController {
    private MicroServiceService mss;
    private TopMenuLinkService topmenuservice;
    private ResourceLoader rl;
    private LangService langservice;


    @Autowired
    public MSController(MicroServiceService mss, TopMenuLinkService topmenuservice, ResourceLoader rl, LangService langservice){
        this.mss = mss;
        this.topmenuservice = topmenuservice;
        this.rl = rl;
        this.langservice = langservice;
    }

    @GetMapping("{servicename}/{page}")
    public String get_servicename_page(@PathVariable String servicename, @PathVariable String page, Model model, HttpSession session) throws IOException {
        System.out.println("MSController");
        MicroService ms = mss.findMSByName(servicename);
        if(ms != null){
            Resource resource;
            if(session != null && session.getAttribute("user") != null){
                User user = (User)session.getAttribute("user");
                System.out.println("Getting: " + "http://localhost:"+ms.getPort()+"/servicepages/"+servicename+"/"+page+"?user_id="+user.getId());
                resource = rl.getResource("http://localhost:"+ms.getPort()+"/servicepages/"+servicename+"/"+page+"?user_id="+user.getId());
            }
            else{
                System.out.println("Getting: " + "http://localhost:"+ms.getPort()+"/servicepages/"+servicename+"/"+page);
                resource = rl.getResource("http://localhost:"+ms.getPort()+"/servicepages/"+servicename+"/"+page);
            }

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
    private List<TopMenuLink> getTopMenuLink(HttpServletRequest hsr){
        return topmenuservice.findAll(getLanguage(hsr));
    }

    @ModelAttribute("language")
    public String getLanguage(HttpServletRequest hsr){
        return langservice.getUserLanguage(hsr.getSession());
    }

    @ModelAttribute("alternativelanguage")
    public String getAlternativeLanguage(HttpServletRequest hsr) {
        return langservice.getUserAlternativeLanguage(hsr.getSession());
    }

    @ModelAttribute("page")
    private String setPage(HttpServletRequest hsr){
        return hsr.getRequestURI();
    }
}
