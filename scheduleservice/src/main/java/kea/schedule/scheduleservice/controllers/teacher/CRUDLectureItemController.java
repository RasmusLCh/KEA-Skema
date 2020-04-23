package kea.schedule.scheduleservice.controllers.teacher;

import kea.schedule.scheduleservice.components.MSSession;
import kea.schedule.scheduleservice.controllers.CRUDAbstractController;
import kea.schedule.scheduleservice.models.Course;
import kea.schedule.scheduleservice.models.Lecture;
import kea.schedule.scheduleservice.models.LectureItem;
import kea.schedule.scheduleservice.models.LectureSubject;
import kea.schedule.scheduleservice.services.CourseService;
import kea.schedule.scheduleservice.services.LectureItemService;
import kea.schedule.scheduleservice.services.LectureService;
import kea.schedule.scheduleservice.services.LectureSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/servicepages/KEA-Schedule-Teacher/items/")
public class CRUDLectureItemController extends CRUDAbstractController<LectureItem, LectureItemService> {
    @Value("${ms.port.teacher:7512}")
    int teacherport;

    private LectureSubjectService lecturesubjectservice;
    private LectureService lectureservice;
    private CourseService courseservice;

    @Autowired
    public CRUDLectureItemController(LectureItemService lectureitemservice, LectureSubjectService lecturesubjectservice, LectureService lectureservice, CourseService courseservice, @Value("${ms.port.service:7510}") int port) {
        super("teacher/lectureitems/", "lectureitem", "servicepages/KEA-Schedule-Teacher/items/", lectureitemservice, port);
        this.lectureservice = lectureservice;
        this.courseservice = courseservice;
        this.lecturesubjectservice = lecturesubjectservice;
    }

    @GetMapping({"index", ""})
    @Override
    public String get_root_index(Model model)
    {
        System.out.println("Root");
        model.addAttribute(modelname + "s", service.findAllByLectureSubjectId(lecturesubjectservice.getSelectedLectureSubjectId()));
        return path + "index";
    }

    @PostMapping({"index", ""})
    public String post_root_index(@RequestParam(value = "selectedlecturesubjectid", required = true, defaultValue = "0") int selectedlecturesubjectid, Model model)
    {
        lecturesubjectservice.setSelectedLectureSubject(selectedlecturesubjectid, model);
        System.out.println("Root selectedlecturesubjectid = " + selectedlecturesubjectid);
        model.addAttribute(modelname + "s", service.findAllByLectureSubjectId(lecturesubjectservice.getSelectedLectureSubjectId()));
        return path + "index";
    }


    @ModelAttribute("selectedcourseid")
    public int selected_courseid(){
        return courseservice.getSelectedCourseId();
    }

    @ModelAttribute("selectedcourse")
    public Course selected_course(){
        return courseservice.getSelectedCourse();
    }

    @ModelAttribute("selectedlectureid")
    public int selected_lectureid() {
        return lectureservice.getSelectedLectureId();
    }

    @ModelAttribute("selectedlecture")
    public Lecture selected_lecture() {
        return lectureservice.getSelectedLecture();
    }

    @ModelAttribute("selectedlecturesubjectid")
    public int selected_lecturesubjectid() {
        return lecturesubjectservice.getSelectedLectureSubjectId();
    }

    @ModelAttribute("selectedlecturesubject")
    public LectureSubject selected_lecturesubject() {
        return lecturesubjectservice.getSelectedLectureSubject();
    }

    @ModelAttribute("lecturesubjects")
    public List<LectureSubject> modelattribute_lecturesubjects() {
        System.out.println("Sizeeeee " + lecturesubjectservice.findAllByLectureId(lectureservice.getSelectedLectureId()).size());
        return lecturesubjectservice.findAllByLectureId(lectureservice.getSelectedLectureId());
    }
}