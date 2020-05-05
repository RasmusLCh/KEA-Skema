package kea.schedule.controllers.admin;

import kea.schedule.models.MicroServiceElement;
import kea.schedule.models.MicroServiceOption;
import kea.schedule.services.MicroServiceOptionService;
import kea.schedule.services.MicroServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin/microserviceoptions/")
public class CRUDMicroServiceOptionController extends MSCRUDAbstractController<MicroServiceOption, MicroServiceOptionService> {

    @Autowired
    public CRUDMicroServiceOptionController(MicroServiceOptionService msoptionservice, MicroServiceService msservice){
        super("microserviceoptions/", "microserviceoption", msoptionservice, msservice);
    }

    @Override
    @PostMapping("/edit")
    public String post_edit(@ModelAttribute @Valid MicroServiceOption e, BindingResult result, HttpSession session, Model model)
    {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute(modelname, e);
        if (result.hasErrors()) {
            return path + "edit";
        }
        if(e instanceof MicroServiceElement && ((MicroServiceElement) e).getMicroservice() == null && session.getAttribute("selectedmicroserviceid") != null){
            ((MicroServiceElement)e).setMicroservice(msservice.findById((int)session.getAttribute("selectedmicroserviceid")));
        }
        else if(e instanceof MicroServiceElement){
            ((MicroServiceElement) e).setMicroservice(msservice.findById(((MicroServiceElement) e).getMicroservice().getId()));
        }
        MicroServiceOption mso = service.findById(e.getId());
        mso.setActive(e.getActive());
        mso.setDescription(e.getDescription());
        mso.setName(e.getName());
        mso.setPriority(e.getPriority());
        service.edit(mso);
        return "redirect:/" + path + "view/" + e.getId() + "/";
    }
}
