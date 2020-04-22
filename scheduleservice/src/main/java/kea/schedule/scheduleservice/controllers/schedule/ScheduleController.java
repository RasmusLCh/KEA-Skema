package kea.schedule.scheduleservice.controllers.schedule;

import kea.schedule.scheduleservice.components.MSSession;
import kea.schedule.scheduleservice.models.Course;
import kea.schedule.scheduleservice.services.CourseService;
import kea.schedule.scheduleservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/servicepages/KEA-Schedule/")
public class ScheduleController {
    @Value("${ms.port.student:7511}")
    int studentport;
    UserService userservice;
    MSSession session;
    CourseService courseservice;
    public ScheduleController(MSSession session, UserService userservice, CourseService courseservice){
        this.session = session;
        this.userservice = userservice;
        this.courseservice = courseservice;
    }

    @GetMapping({"", "/", "index", "index.eng"})
    public String get_root(HttpServletRequest hsr){
        if(hsr.getLocalPort() == studentport){
            if(userservice.getCurrentUserId() != 0){
                session.setAttribute("schcourses", userservice.getCurrentUserId());
                System.out.println("Num courses: " + courseservice.getUserCourses().size());
            }
            else{
                System.out.println("No userid");
            }
            return "schedule/index";
        }
        return "forbidden";
    }
}
