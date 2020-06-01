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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * CRUD controller for LectureSubject
 * html files mut be placed in templates/teacher/lecturesubjects/
 * url access /servicepages/KEA-Schedule-Teacher/subjects/
 * */

@Controller
@RequestMapping("/servicepages/KEA-Schedule-Teacher/subjects/")
public class CRUDLectureSubjectController extends CRUDAbstractController<LectureSubject, LectureSubjectService> {
    @Value("${ms.port.teacher:7512}")
    int teacherport;

    private LectureService lectureservice;
    private CourseService courseservice;

    @Autowired
    public CRUDLectureSubjectController(LectureSubjectService lecturesubjectservice, LectureService lectureservice, CourseService courseservice, @Value("${ms.port.service:7510}") int port) {
        super("teacher/lecturesubjects/", "lecturesubject", "servicepages/KEA-Schedule-Teacher/subjects/", lecturesubjectservice, port);
        this.lectureservice = lectureservice;
        this.courseservice = courseservice;
    }

    @GetMapping({"index", ""})
    @Override
    public String get_root_index(Model model)
    {
        System.out.println("Root");
        model.addAttribute(modelname + "s", service.findAllByLectureId(lectureservice.getSelectedLectureId()));
        return path + "index";
    }

    @PostMapping({"index", ""})
    public String post_root_index(@RequestParam(value = "selectedlectureid", required = true, defaultValue = "0") int lectureid, Model model)
    {
        lectureservice.setSelectedLecture(lectureid, model);
        System.out.println("Root");
        model.addAttribute(modelname + "s", service.findAllByLectureId(lectureservice.getSelectedLectureId()));
        return path + "index";
    }

    @PostMapping("/edit")
    public String post_edit(@ModelAttribute @Valid LectureSubject e, BindingResult result, Model model)
    {
        model.addAttribute(modelname, e);
        if (result.hasErrors()) {
            return path + "edit";
        }
        LectureSubject l = service.findById(e.getId());
        e.setLectureitems(l.getLectureitems());
        System.out.println("Priority: " +e.getPriority());
        service.edit(e);
        return "redirect:/" + webaddr + "view/" + e.getId() + "/";
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

    @ModelAttribute("lectures")
    public List<Lecture> modelattribute_lectures() {
        //Should only return the courses the teacher has access too.
        return lectureservice.findAllByCourseId(courseservice.getSelectedCourseId());
    }
}