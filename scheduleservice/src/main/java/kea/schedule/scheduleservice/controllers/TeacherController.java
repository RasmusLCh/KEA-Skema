package kea.schedule.scheduleservice.controllers;

import kea.schedule.scheduleservice.components.MSSession;
import kea.schedule.scheduleservice.models.Course;
import kea.schedule.scheduleservice.services.CourseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/servicepages/KEA-Schedule-Teacher/")
public class TeacherController {
    @Value("${ms.port.teacher:7512}")
    int teacherport;

    private CourseService courseservice;

    public TeacherController(CourseService courseservice){
        this.courseservice = courseservice;
    }

    @GetMapping({"", "/", "index", "index.eng"})
    public String get_root(HttpServletRequest hsr, Model model){
        System.out.println("get_root teacher");
        if(hsr.getLocalPort() == teacherport){
            System.out.println(model.getAttribute("selectedcourseid"));
            return "teacher/index";
        }
        return "forbidden";
    }

    @PostMapping({"", "/", "index", "index.eng"})
    public String post_root(@RequestParam(name="selectedcourseid", required=true) int selectedcourseid, HttpServletRequest hsr, Model model){
        if(hsr.getLocalPort() == teacherport){
            courseservice.setSelectedcourse(selectedcourseid, model);

            System.out.println(model.getAttribute("selectedcourseid"));
            return "redirect:index";
        }
        return "forbidden";
    }

    @ModelAttribute("selectedcourseid")
    public int selected_courseid(HttpSession session){
        return courseservice.getSelectedCourseId();
    }

    @ModelAttribute("selectedcourse")
    public Course selected_course(HttpSession session){
        return courseservice.getSelectedCourse();
    }

    @ModelAttribute("courses")
    public List<Course> modelattribute_courses(MSSession session){
        //Should only return the courses the teacher has access too.
        return courseservice.findAllByAccess();
    }
}
