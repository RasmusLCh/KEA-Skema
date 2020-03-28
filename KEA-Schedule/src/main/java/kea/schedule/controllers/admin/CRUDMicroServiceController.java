package kea.schedule.controllers.admin;

import kea.schedule.moduls.MicroService;
import kea.schedule.services.GroupService;
import kea.schedule.services.MicroServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/services/")
public class CRUDMicroServiceController extends MSCRUDAbstractController<MicroService, MicroServiceService> {

    @Autowired
    GroupService gs;

    @Autowired
    public CRUDMicroServiceController(MicroServiceService service, MicroServiceService msservice){
        super("services/", "microservice", service, msservice);
    }

    @GetMapping({"", "index"})
    @Override
    public String get_root_index(Model model, HttpSession session){
        List<MicroService> microservices = msservice.findAll();
        System.out.println("services size = " + microservices.size());
        model.addAttribute("microservices", microservices);

        return "admin/services/index";
    }


    @GetMapping("create")
    @Override
    public String get_create(Model model, HttpSession session, MicroService modelojb) {
        model.addAttribute("accessgroups", gs.findAll());
        return super.get_create(model, session, modelojb);
    }

    @GetMapping("/edit/{id}")
    @Override
    public String get_edit(@PathVariable int id, Model model, HttpSession session) {
        model.addAttribute("accessgroups", gs.findAll());
        return super.get_edit(id, model, session);
    }

}
