package microservice.infrastructure.controllers.admin;

import microservice.infrastructure.models.MicroServiceElement;
import microservice.infrastructure.models.MicroServiceOption;
import microservice.infrastructure.services.MicroServiceOptionService;
import microservice.infrastructure.services.MicroServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * CRUD controller for MicroServiceOption
 * html files mut be placed in templates/admin/microserviceoptions/
 * url access /admin/microserviceoptions/
 * */

@Controller
@RequestMapping("/admin/microserviceoptions/")
public class CRUDMicroServiceOptionController extends MSCRUDAbstractController<MicroServiceOption, MicroServiceOptionService> {

    @Autowired
    public CRUDMicroServiceOptionController(MicroServiceOptionService msoptionservice, MicroServiceService msservice){
        super("microserviceoptions/", "microserviceoption", msoptionservice, msservice);
    }

    /**
     * We dont want to override any data that cannot be set throuhg the form, so we load the MicroServiceOption, and then update values that are available to set in the form in the object from the database, then save the object from the database and discard the object that was sent from the form.
     * */
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
