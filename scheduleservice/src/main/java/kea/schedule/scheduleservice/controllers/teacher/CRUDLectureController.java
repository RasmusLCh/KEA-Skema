package kea.schedule.scheduleservice.controllers.teacher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/servicepages/KEA-Schedule-Teacher/lectures/")
public class CRUDLectureController {
    @Value("${ms.port.teacher:7512}")
    int teacherport;



}
