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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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

    @PostMapping({"index", ""})
    public String post_root_index(@RequestParam(name="selectedcourseid", required=true) int selectedcourseid, Model model, HttpServletRequest hsr)
    {
        if(hsr.getLocalPort() == teacherport){
            courseservice.setSelectedcourse(selectedcourseid, model);

            System.out.println(model.getAttribute("selectedcourseid"));
            return "redirect:/"+webaddr+"index";
        }
        return "redirect:forbidden";
    }

    @PostMapping("/edit")
    public String post_edit(@ModelAttribute @Valid Lecture e, BindingResult result, Model model)
    {
        model.addAttribute(modelname, e);
        if (result.hasErrors()) {
            return path + "edit";
        }
        Lecture l = service.findById(e.getId());
        e.setLecturesubjects(l.getLecturesubjects());
        service.edit(e);
        return "redirect:/" + webaddr + "view/" + e.getId() + "/";
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
