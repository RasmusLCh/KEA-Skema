package kea.schedule.scheduleservice.controllers.admin;

import kea.schedule.scheduleservice.controllers.CRUDAbstractController;
import kea.schedule.scheduleservice.models.Course;
import kea.schedule.scheduleservice.services.CourseService;
import kea.schedule.scheduleservice.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/servicepages/KEA-Schedule-Admin/courses/")
public class CRUDCourseController extends CRUDAbstractController<Course, CourseService> {
    private GroupService grpservice;

    @Autowired
    public CRUDCourseController(CourseService courseservice, GroupService grpservice, @Value("${ms.port.service:7510}") int port){
        super("admin/courses/", "course", "servicepages/KEA-Schedule-Admin/courses/", courseservice, port);
        this.grpservice = grpservice;
    }

    @Override
    @GetMapping("create")
    public String get_create(Model model, Course couse) {
        model.addAttribute("groups", grpservice.findAll());
        return super.get_create(model, couse);
    }

    @Override
    @GetMapping("/edit/{id}")
    public String get_edit(@PathVariable int id, Model model)
    {
        model.addAttribute("groups", grpservice.findAll());
        return super.get_edit(id, model);
    }
}
