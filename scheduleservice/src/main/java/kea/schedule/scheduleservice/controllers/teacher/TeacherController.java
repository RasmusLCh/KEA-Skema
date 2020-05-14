package kea.schedule.scheduleservice.controllers.teacher;

import kea.schedule.scheduleservice.components.MSSession;
import kea.schedule.scheduleservice.models.*;
import kea.schedule.scheduleservice.services.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/servicepages/KEA-Schedule-Teacher/")
public class TeacherController {
    @Value("${ms.port.teacher:7512}")
    int teacherport;
    private MSSession session;
    private CourseService courseservice;
    private ScheduleService scheduleservice;

    public TeacherController(CourseService courseservice, ScheduleService scheduleservice, MSSession session){
        this.courseservice = courseservice;
        this.scheduleservice = scheduleservice;
        this.session = session;

    }

    @GetMapping({"", "/", "index", "index.eng", "index.dk"})
    public String get_root(HttpServletRequest hsr, Model model){
        System.out.println("get_root teacher");
        if(hsr.getLocalPort() == teacherport){
            System.out.println(model.getAttribute("selectedcourseid"));
            return "teacher/index";
        }
        return "forbidden";
    }

    @PostMapping({"", "/", "index", "index.eng", "index.dk"})
    public String post_root(@RequestParam(name="selectedcourseid", required=true) int selectedcourseid, HttpServletRequest hsr, Model model){
        if(hsr.getLocalPort() == teacherport){
            courseservice.setSelectedcourse(selectedcourseid, model);

            System.out.println(model.getAttribute("selectedcourseid"));
            return "redirect:index";
        }
        return "forbidden";
    }

    @GetMapping({"qa.eng", "qa"})
    public String get_qa_eng(HttpServletRequest hsr, Model model){
        if(hsr.getLocalPort() == teacherport){
            return "teacher/qa";
        }
        return "forbidden";
    }

    @GetMapping({"qa.dk"})
    public String get_qa_dk(HttpServletRequest hsr, Model model){
        if(hsr.getLocalPort() == teacherport){
            return "teacher/qa";
        }
        return "forbidden";
    }

    @PostMapping("uploadcoursecsv")
    public String post_uploadcoursecsv(@RequestParam("courseplancsv") MultipartFile courseplancsv, HttpServletRequest hsr)  {
        if(hsr.getLocalPort() == teacherport){
            try {
                scheduleservice.importCSV(courseplancsv);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "redirect:index";
        }
        return "forbidden";
    }

    @ModelAttribute("selectedcourseid")
    public int selected_courseid(){
        return courseservice.getSelectedCourseId();
    }

    @ModelAttribute("selectedcourse")
    public Course selected_course(){
        return courseservice.getSelectedCourse();
    }

    @ModelAttribute("courses")
    public List<Course> modelattribute_courses(){
        //Should only return the courses the teacher has access too.
        return courseservice.findAllByAccess();
    }

    @ModelAttribute("userid")
    public int getUserId(){
        return session.getUserId();
    }
}
