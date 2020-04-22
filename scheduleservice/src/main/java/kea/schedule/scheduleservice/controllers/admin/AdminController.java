package kea.schedule.scheduleservice.controllers.admin;

import kea.schedule.scheduleservice.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/servicepages/KEA-Schedule-Admin/")
public class AdminController {
    private CourseService courseservice;

    @Value("${ms.port.service:7510}")
    int serviceport;

    @Autowired
    public AdminController(CourseService courseservice){
        this.courseservice = courseservice;
    }

    @GetMapping({"", "index", "index.eng"})
    public String get_root(HttpServletRequest hsr){
        if(hsr.getLocalPort() == serviceport){
            return "admin/index";
        }
        return "forbidden";
    }
}