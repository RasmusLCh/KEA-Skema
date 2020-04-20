package kea.schedule.scheduleservice.controllers.teacher;

import kea.schedule.scheduleservice.components.MSSession;
import kea.schedule.scheduleservice.controllers.CRUDAbstractController;
import kea.schedule.scheduleservice.models.Course;
import kea.schedule.scheduleservice.models.Lecture;
import kea.schedule.scheduleservice.services.CourseService;
import kea.schedule.scheduleservice.services.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/servicepages/KEA-Schedule-Teacher/lectures/")
public class CRUDLectureController extends CRUDAbstractController<Lecture, LectureService> {
    @Value("${ms.port.teacher:7512}")
    int teacherport;

    private CourseService courseservice;

    @Autowired
    public CRUDLectureController(LectureService lectureservice, CourseService courseservice, @Value("${ms.port.service:7510}") int port){
        super("teacher/lectures/", "lecture", "servicepages/KEA-Schedule-Teacher/lectures/", lectureservice, port);
        this.courseservice = courseservice;
    }

    @GetMapping({"index", ""})
    @Override
    public String get_root_index(Model model)
    {
        System.out.println("Root");
        model.addAttribute(modelname + "s", service.findAllByCourseId(courseservice.getSelectedCourseId()));
        return path + "index";
    }

    public CRUDLectureController(CourseService courseservice){
        this.courseservice = courseservice;
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
