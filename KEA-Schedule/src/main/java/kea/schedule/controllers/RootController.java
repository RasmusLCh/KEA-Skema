package kea.schedule.controllers;

import kea.schedule.moduls.PageInjection;
import kea.schedule.moduls.TopMenuLink;
import kea.schedule.services.LangService;
import kea.schedule.services.MicroServiceService;
import kea.schedule.services.PageInjectionService;
import kea.schedule.services.TopMenuLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/")
public class RootController {
    private PageInjectionService pageinjectionservice;
    private TopMenuLinkService topmenuservice;
    private LangService langservice;
    private MicroServiceService msservice;

    @Autowired
    public RootController(PageInjectionService pageinjectionservice, TopMenuLinkService topmenuservice, LangService langservice, MicroServiceService msservice){
        this.pageinjectionservice = pageinjectionservice;
        this.topmenuservice = topmenuservice;
        this.langservice = langservice;
        this.msservice = msservice;
    }

    @GetMapping({"index", ""})
    public String get_root(Model model, HttpServletRequest hsr){
        if(getLanguage(hsr).equalsIgnoreCase("dk")){
            return "redirect:/index.dk";
        }
        return "redirect:/index.eng";
    }



    @GetMapping({"index.eng", "index.dk"})
    public String get_index(Model model, HttpSession session) throws IOException {
        System.out.println(session.getId());
        return "index";
    }

    @GetMapping("settings.eng")
    public String get_sessing_eng(Model model){
        model.addAttribute("microservices", msservice.findAll());
        System.out.println("Services = " + msservice.findAll().size());

        return "settings_eng";
    }

    @GetMapping("settings.dk")
    public String get_sessing_dk(Model model){
        return "settings_dk";
    }

    @PostMapping("setLanguage")
    public String post_setLanguage(@RequestParam("page") String page, @RequestParam("language") String language, HttpSession session){
        System.out.println("Lang " + language);
        langservice.setLanguage(language, session);
        page = langservice.switchPageLanguage(page, language);
        return "redirect:"+page;
    }

    @ModelAttribute("topmenu")
    private List<TopMenuLink> getTopMenuLink(HttpServletRequest hsr){
        return topmenuservice.findAll(getLanguage(hsr));
    }

    @ModelAttribute("pageinjections_js")
    private List<PageInjection> getJSPageInjection(HttpServletRequest hsr){
        System.out.println("pathinfo: " + hsr.getPathInfo()+ " " + hsr.getRequestURI());
        List<PageInjection> pi = pageinjectionservice.getJSPageInjection(hsr.getRequestURI());
        System.out.println("Page JS injections found " + pi.size());
        for(PageInjection p : pi){
            System.out.println(p.getPage() + ", " + p.getType() + ": " + p.getData());
        }
        return pi;
    }

    @ModelAttribute("pageinjections_css")
    private List<PageInjection> getCSSPageInjection(HttpServletRequest hsr){
        System.out.println("pathinfo: " + hsr.getPathInfo()+ " " + hsr.getRequestURI());
        List<PageInjection> pi = pageinjectionservice.getCSSPageInjection(hsr.getRequestURI());
        System.out.println("Page CSS injections found " + pi.size());
        for(PageInjection p : pi){
            System.out.println(p.getPage() + ", " + p.getType() + ": " + p.getData());
        }
        return pi;
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
