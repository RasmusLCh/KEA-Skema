package kea.schedule.scheduleservice.controllers.teacher;

import kea.schedule.scheduleservice.components.MSSession;
import kea.schedule.scheduleservice.controllers.CRUDAbstractController;
import kea.schedule.scheduleservice.models.Course;
import kea.schedule.scheduleservice.models.Lecture;
import kea.schedule.scheduleservice.models.LectureSubject;
import kea.schedule.scheduleservice.services.CourseService;
import kea.schedule.scheduleservice.services.LectureService;
import kea.schedule.scheduleservice.services.LectureSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/servicepages/KEA-Schedule-Teacher/subjects/")
public class CRUDLectureSubjectController extends CRUDAbstractController<LectureSubject, LectureSubjectService> {
    @Value("${ms.port.teacher:7512}")
    int teacherport;

    private LectureService lectureservice;
    private CourseService courseservice;

    @Autowired
    public CRUDLectureSubjectController(LectureSubjectService lecturesubjectservice, LectureService lectureservice, CourseService courseservice, @Value("${ms.port.service:7510}") int port) {
        super("teacher/subjects/", "lecturesubject", "servicepages/KEA-Schedule-Teacher/subjects/", lecturesubjectservice, port);
        this.lectureservice = lectureservice;
        this.courseservice = courseservice;
    }

    @GetMapping({"index", ""})
    public String get_root_index(Model model)
    {
        System.out.println("Root");
        model.addAttribute(modelname + "s", service.findAllByLectureId(lectureservice.getSelectedLectureId()));
        return path + "index";
    }

    @GetMapping({"manage"})
    public String get_root_index(@RequestParam(value = "lectureid", required = false, defaultValue = "0") int lectureid, Model model) {
        System.out.println("Manage " + lectureid);
        if (lectureid != 0) {
            lectureservice.setSelectedcourse(lectureid, model);
        }
        return "redirect:/"+webaddr+"index";
    }

    @ModelAttribute("selectedlecturid")
    public int selected_lectureid(HttpSession session) {
        return lectureservice.getSelectedLectureId();
    }

    @ModelAttribute("selectedlecture")
    public Lecture selected_course(HttpSession session) {
        return lectureservice.getSelectedLecture();
    }

    @ModelAttribute("lectures")
    public List<Lecture> modelattribute_courses(MSSession session) {
        //Should only return the courses the teacher has access too.
        return lectureservice.findAllByCourseId(courseservice.getSelectedCourseId());
    }
}