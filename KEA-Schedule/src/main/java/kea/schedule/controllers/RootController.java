package kea.schedule.controllers;

import kea.schedule.models.*;
import kea.schedule.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class RootController {
    private PageInjectionService pageinjectionservice;
    private TopMenuLinkService topmenuservice;
    private LangService langservice;
    private MicroServiceService msservice;
    private UserService userservice;
    private AuthenticationService authservice;
    private UserMicroServiceOptionService usermicroserviceoptionservice;


    @Autowired
    public RootController(PageInjectionService pageinjectionservice, TopMenuLinkService topmenuservice, LangService langservice, MicroServiceService msservice, AuthenticationService authservice, UserService userservice, UserMicroServiceOptionService usermicroserviceoptionservice){
        this.pageinjectionservice = pageinjectionservice;
        this.topmenuservice = topmenuservice;
        this.langservice = langservice;
        this.msservice = msservice;
        this.authservice = authservice;
        this.userservice = userservice;
        this.usermicroserviceoptionservice = usermicroserviceoptionservice;
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
    public String get_settings_eng(Model model, HttpSession session){
        model.addAttribute("microservices", msservice.findAll());
        if(session.getAttribute("curuser") != null){
            System.out.println("getUserMicroServiceOptions");
            User curuser = (User)session.getAttribute("curuser");
            model.addAttribute("usermicroserviceoptions", usermicroserviceoptionservice.getUserMicroServiceOptions(curuser));
        }
        else{
            Map<String, UserMicroServiceOption> mopts = new HashMap();
            model.addAttribute("usermicroserviceoptions", mopts);
        }
        return "settings_eng";
    }

    @GetMapping("settings.dk")
    public String get_settings_dk(Model model){
        model.addAttribute("microservices", msservice.findAll());
        return "settings_dk";
    }

    @PostMapping("setLanguage")
    public String post_setLanguage(@RequestParam("page") String page, @RequestParam("language") String language, HttpSession session){
        System.out.println("Lang " + language);
        langservice.setLanguage(language, session);
        page = langservice.switchPageLanguage(page, language);
        return "redirect:"+page;
    }

    @PostMapping("login")
    public String post_login(@RequestParam("identifier") String identifier, @RequestParam("password") String password, HttpSession session){
        String page = "index";
        boolean authenticated = authservice.Authenticate(identifier, password);
        if(!authenticated){
            authenticated = authservice.Authenticate(userservice.findByEmail(identifier), password);
        }
        if(authenticated){
            //User authenticated
            System.out.println("User authenticated");
            User curuser = (User)session.getAttribute("curuser");
            for (Group grp: ((User)session.getAttribute("curuser")).getGroups()){
                System.out.println("\tAccess groups" + grp.getName());
            }
            if(authservice.hasAccess("DAT18A")){
                System.out.println("User has access to DAT18A");
            }
            if(authservice.hasAccess("DAT18B")){
                System.out.println("User has access to DAT18B");
            }
            if(authservice.hasAccess("DAT18")){
                System.out.println("User has access to DAT18");
            }
            langservice.setLanguage(curuser.getLanguage(), session);
            String language = curuser.getLanguage();
            page = langservice.switchPageLanguage(page, language);
            return "redirect:"+page;
        }
        //User authentication failed!
        System.out.println("User authentication failed!");

        return "redirect:"+page;
    }

    @GetMapping("logout")
    public String get_logout(HttpSession session){
        session.setAttribute("curuser", null);
        return "redirect:/index";
    }


    @ModelAttribute("topmenu")
    private List<TopMenuLink> getTopMenuLink(HttpServletRequest hsr){
        System.out.println("topmenu lang = " + getLanguage(hsr));
        return topmenuservice.findAllByLanguageAndAccess(getLanguage(hsr));
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

    @ModelAttribute("authenticated")
    public boolean getAuthenticated(){
        return authservice.isAuthenticated();
    }
}
