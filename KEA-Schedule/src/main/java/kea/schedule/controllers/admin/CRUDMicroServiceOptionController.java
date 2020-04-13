package kea.schedule.controllers.admin;

import kea.schedule.models.MicroServiceOption;
import kea.schedule.services.MicroServiceOptionService;
import kea.schedule.services.MicroServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/serviceoptions/")
public class CRUDMicroServiceOptionController extends MSCRUDAbstractController<MicroServiceOption, MicroServiceOptionService> {

    @Autowired
    public CRUDMicroServiceOptionController(MicroServiceOptionService msoptionservice, MicroServiceService msservice){
        super("microserviceoptions/", "microserviceoption", msoptionservice, msservice);
    }
}
