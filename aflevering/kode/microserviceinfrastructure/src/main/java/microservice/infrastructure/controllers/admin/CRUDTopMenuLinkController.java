package microservice.infrastructure.controllers.admin;

import microservice.infrastructure.models.TopMenuLink;
import microservice.infrastructure.services.MicroServiceService;
import microservice.infrastructure.services.TopMenuLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * CRUD controller for TopMenuLink
 * html files mut be placed in templates/admin/topmenulinks/
 * url access /admin/topmenulinks/
 * */

@Controller
@RequestMapping("/admin/topmenulinks/")
public class CRUDTopMenuLinkController extends MSCRUDAbstractController<TopMenuLink, TopMenuLinkService> {

    @Autowired
    public CRUDTopMenuLinkController(TopMenuLinkService tmlservice, MicroServiceService msservice){
        super("topmenulinks/", "topmenulink", tmlservice, msservice);
    }
}
