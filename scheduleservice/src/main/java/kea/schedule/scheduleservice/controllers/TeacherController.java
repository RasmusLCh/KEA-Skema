package kea.schedule.scheduleservice.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/servicepages/KEA-Schedule-Teacher/")
public class TeacherController {
    @Value("${ms.port.teacher:7512}")
    int teacherport;

    @GetMapping({"", "/", "index", "index.eng"})
    public String get_root(HttpServletRequest hsr){
        if(hsr.getLocalPort() == teacherport){
            return "teacher/index";
        }
        return "forbidden";
    }

    @GetMapping({"manage.eng"})
    public String get_upload(HttpServletRequest hsr){
        if(hsr.getLocalPort() == teacherport){
            return "teacher/manage";
        }
        return "forbidden";
    }
}
