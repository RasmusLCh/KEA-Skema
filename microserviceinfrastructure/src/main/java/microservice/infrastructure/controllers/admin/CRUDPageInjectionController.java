package microservice.infrastructure.controllers.admin;

import microservice.infrastructure.models.PageInjection;
import microservice.infrastructure.services.MicroServiceService;
import microservice.infrastructure.services.PageInjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The class extends the MSCRUDAbstractController so only path, modename and CRUD service and msservice is specified.
 * */
@Controller
@RequestMapping("/admin/pageinjections/")
public class CRUDPageInjectionController extends MSCRUDAbstractController<PageInjection, PageInjectionService> {

    @Autowired
    public CRUDPageInjectionController(PageInjectionService pageinjectionservice, MicroServiceService msservice){
        super("pageinjections/", "pageinjection", pageinjectionservice, msservice);
    }
}
