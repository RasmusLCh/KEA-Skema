package microservice.infrastructure.controllers.admin;

import microservice.infrastructure.models.TopMenuLink;
import microservice.infrastructure.services.MicroServiceService;
import microservice.infrastructure.services.TopMenuLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The class extends the MSCRUDAbstractController so only path, modename and CRUD service and msservice is specified.
 * */

@Controller
@RequestMapping("/admin/topmenulinks/")
public class CRUDTopMenuLinkController extends MSCRUDAbstractController<TopMenuLink, TopMenuLinkService> {

    @Autowired
    public CRUDTopMenuLinkController(TopMenuLinkService tmlservice, MicroServiceService msservice){
        super("topmenulinks/", "topmenulink", tmlservice, msservice);
    }
}
