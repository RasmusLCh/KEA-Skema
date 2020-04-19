package kea.schedule.scheduleservice.controllers.admin;

import kea.schedule.scheduleservice.controllers.CRUDAbstractController;
import kea.schedule.scheduleservice.models.SubjectPriority;
import kea.schedule.scheduleservice.services.SubjectPriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Note this internally links to the sps folder
 * */
@Controller
@RequestMapping("/servicepages/KEA-Schedule-Admin/subjectpriorities/")
public class CRUDSubjectPriorityController extends CRUDAbstractController<SubjectPriority, SubjectPriorityService> {
    @Value("${ms.port.service:7510}")
    int serviceport;

    @Autowired
    public CRUDSubjectPriorityController(SubjectPriorityService SubjectPriorityservice, @Value("${ms.port.service:7510}") int port){
        super("admin/sps/", "subjectpriority", "servicepages/KEA-Schedule-Admin/subjectpriorities/", SubjectPriorityservice, port);
    }
}
