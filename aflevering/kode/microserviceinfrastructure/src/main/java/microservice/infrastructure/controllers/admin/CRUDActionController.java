package microservice.infrastructure.controllers.admin;

import microservice.infrastructure.models.Action;
import microservice.infrastructure.services.ActionService;
import microservice.infrastructure.services.MicroServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * CRUD controller for Action
 * html files mut be placed in templates/admin/actions/
 * url access /admin/actions/
 * */

@Controller
@RequestMapping("/admin/actions/")
public class CRUDActionController extends MSCRUDAbstractController<Action, ActionService> {

    @Autowired
    public CRUDActionController(ActionService actionservice, MicroServiceService msservice){
        super("actions/", "action", actionservice, msservice);
    }
}
