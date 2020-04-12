package kea.schedule.scheduleservice.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/servicepages/KEA-Schedule-Student/")
public class StudentController {
    @Value("${ms.port.student:7511}")
    int studentport;

    @GetMapping({"", "/", "index", "index.eng"})
    public String get_root(HttpServletRequest hsr){
        if(hsr.getLocalPort() == studentport){
            return "student/index";
        }
        return "forbidden";
    }
}
