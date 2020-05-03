package kea.schedule.controllers.admin;

import kea.schedule.models.MicroService;
import kea.schedule.services.GroupService;
import kea.schedule.services.MicroServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        List<MicroService> microservices = msservice.findAll();
        System.out.println("services size = " + microservices.size());
        model.addAttribute("microservices", microservices);

        return "admin/services/index";
    }


    @GetMapping("create")
    @Override
    public String get_create(Model model, HttpSession session, MicroService modelojb) {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute("accessgroups", gs.findAll());
        return super.get_create(model, session, modelojb);
    }

    @GetMapping("/edit/{id}")
    @Override
    public String get_edit(@PathVariable int id, Model model, HttpSession session) {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute("accessgroups", gs.findAll());
        return super.get_edit(id, model, session);
    }

    @PostMapping("/edit")
    @Override
    public String post_edit(@ModelAttribute @Valid MicroService e, BindingResult result, HttpSession session, Model model)
    {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute(modelname, e);
        if (result.hasErrors()) {
            return path + "edit";
        }
        MicroService ms = service.findById(e.getId());
        ms.setName(e.getName());
        ms.setPort(e.getPort());
        ms.setVersion(e.getVersion());
        ms.setDescription(e.getDescription());
        ms.setEnabled(e.getEnabled());
        ms.setDependencyMicroserviceId(e.getDependencyMicroserviceId());
        ms.setAccessgroups(e.getAccessgroups());
        service.edit(ms);
        return "redirect:/" + path + "view/" + ms.getId() + "/";
    }

}
