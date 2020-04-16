package kea.schedule.scheduleservice.controllers.Admin;

import kea.schedule.scheduleservice.controllers.CRUDAbstractController;
import kea.schedule.scheduleservice.models.Course;
import kea.schedule.scheduleservice.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/servicepages/KEA-Schedule-Admin/courses/")
public class CRUDCourseController extends CRUDAbstractController<Course, CourseService> {
    @Value("${ms.port.service:7510}")
    int serviceport;

    @Autowired
    public CRUDCourseController(CourseService courseservice, @Value("${ms.port.service:7510}") int port){
        super("admin/courses/", "course", "servicepages/KEA-Schedule-Admin/courses/", courseservice, port);
    }
}
