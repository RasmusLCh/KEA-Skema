package kea.schedule.scheduleservice.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/servicepages/scheduleserviceteacher/")
public class TeacherController {
    @Value("${ms.port.teacher:7512}")
    int teacherport;

    @GetMapping({"", "/", "index"})
    public String get_root(HttpServletRequest hsr){
        if(hsr.getLocalPort() == teacherport){
            return "teacher/index";
        }
        return "forbidden";
    }
}
