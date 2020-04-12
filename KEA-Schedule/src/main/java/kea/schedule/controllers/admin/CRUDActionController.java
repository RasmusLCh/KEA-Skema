package kea.schedule.controllers.admin;

import kea.schedule.models.Action;
import kea.schedule.services.ActionService;
import kea.schedule.services.MicroServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/actions/")
public class CRUDActionController extends MSCRUDAbstractController<Action, ActionService> {

    @Autowired
    public CRUDActionController(ActionService actionservice, MicroServiceService msservice){
        super("actions/", "action", actionservice, msservice);
    }
}
