package microservice.infrastructure.services;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Service that keep track of current user language.
 * */

@Service
public class LangService {

    public String getUserLanguage(HttpSession session){
        Object language = session.getAttribute("language");
        if(language != null){
            System.out.println("No user language found " + (String) language);
            return (String) language;
        }
        System.out.println("returning default language");
        return "eng";
    }

    public String getUserAlternativeLanguage(HttpSession session){
        if(getUserLanguage(session).equalsIgnoreCase("eng")){
            return "dk";
        }
        return "eng";
    }

    /*
    * Tries to switch the page to the one that matches the language.
    * */
    public String switchPageLanguage(String page, String curlanguage){
        String prevlang = "dk";
        if(curlanguage.equalsIgnoreCase("dk")){
            prevlang = "eng";
        }
        page = page.replace(prevlang, curlanguage);
        return page;
    }

    public void setLanguage(String language, HttpSession session){
        System.out.println("Set language..................." + language);
        session.setAttribute("language", language);
        System.out.println("ID: " + session.getId());
    }

}
