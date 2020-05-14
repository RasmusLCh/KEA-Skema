package microservice.infrastructure.controllers.admin;

import microservice.infrastructure.models.MicroService;
import microservice.infrastructure.services.GroupService;
import microservice.infrastructure.services.MicroServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * The class extends the MSCRUDAbstractController so only path, modename and CRUD service and msservice is specified.
 * Since we need to include data from GroupService create and edit are overriden
 * */

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

    /*
     * The default get_edit is overriden to be able to add accessgroups to the Model
     * */
    @GetMapping("create")
    @Override
    public String get_create(Model model, HttpSession session, MicroService modelojb) {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute("accessgroups", gs.findAll());
        return super.get_create(model, session, modelojb);
    }

    /*
    * The default get_edit is overriden to be able to add accessgroups to the Model
    * */
    @GetMapping("/edit/{id}")
    @Override
    public String get_edit(@PathVariable int id, Model model, HttpSession session) {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute("accessgroups", gs.findAll());
        return super.get_edit(id, model, session);
    }

    /**
     * The default post_edit is overrriden, since we only update part of the microservice.
     * */
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
