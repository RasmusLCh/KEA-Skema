package proof.concept.modules.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import proof.concept.modules.modules.PageInjection;
import proof.concept.modules.modules.TopMenuLink;
import proof.concept.modules.services.PageInjectionService;
import proof.concept.modules.services.TopMenuLinkService;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/")
public class RootController {
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private PageInjectionService pageinjectionservice;
    @Autowired
    private TopMenuLinkService topmenuservice;



    @GetMapping({"index", ""})
    public String get_index(Model model) throws IOException {
        return "index";
    }

    @ModelAttribute("topmenu")
    private List<TopMenuLink> getTopMenuLink(){
        return topmenuservice.findAll();
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
}
