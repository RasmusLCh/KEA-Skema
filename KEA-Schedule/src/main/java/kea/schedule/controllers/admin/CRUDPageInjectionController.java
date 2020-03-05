package kea.schedule.controllers.admin;

import kea.schedule.moduls.PageInjection;
import kea.schedule.services.MicroServiceService;
import kea.schedule.services.PageInjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/pageinjections")
public class CRUDPageInjectionController extends MSCRUDAbstractController<PageInjection, PageInjectionService> {

    @Autowired
    public CRUDPageInjectionController(PageInjectionService pageinjectionservice, MicroServiceService msservice){
        super("pageinjections", pageinjectionservice, msservice);
    }
}
