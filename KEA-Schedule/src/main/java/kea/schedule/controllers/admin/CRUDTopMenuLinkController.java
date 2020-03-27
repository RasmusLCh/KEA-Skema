package kea.schedule.controllers.admin;

import kea.schedule.moduls.TopMenuLink;
import kea.schedule.services.MicroServiceService;
import kea.schedule.services.TopMenuLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/topmenulinks/")
public class CRUDTopMenuLinkController extends MSCRUDAbstractController<TopMenuLink, TopMenuLinkService> {

    @Autowired
    public CRUDTopMenuLinkController(TopMenuLinkService tmlservice, MicroServiceService msservice){
        super("topmenulinks/", "topmenulink", tmlservice, msservice);
    }
}
