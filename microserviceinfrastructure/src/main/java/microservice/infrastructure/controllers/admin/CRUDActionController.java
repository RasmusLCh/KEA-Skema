package microservice.infrastructure.controllers.admin;

import microservice.infrastructure.models.Action;
import microservice.infrastructure.services.ActionService;
import microservice.infrastructure.services.MicroServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The class extends the MSCRUDAbstractController so only path, modename and CRUD service and msservice is specified.
 * */

@Controller
@RequestMapping("/admin/actions/")
public class CRUDActionController extends MSCRUDAbstractController<Action, ActionService> {

    @Autowired
    public CRUDActionController(ActionService actionservice, MicroServiceService msservice){
        super("actions/", "action", actionservice, msservice);
    }
}
