package kea.schedule.controllers.admin;

import kea.schedule.moduls.MicroService;
import kea.schedule.moduls.ModelInterface;
import kea.schedule.services.CRUDServiceInterface;
import kea.schedule.services.MicroServiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * Abstract implementation of a CRUD controller.
 *
 * This abstract implementation expect a POJO object to be created.
 * The POJO object must implement ModelInterface
 * The POJO can get simple datatypes set from the CRUDControllerAbstract.
 *
 * For more information about how to implement the CRUDControllerAbstract class, please see http://morcms.dk/wiki/CRUDControllerAbstract
 * */

@Controller
public abstract class MSCRUDAbstractController<E extends ModelInterface, S extends CRUDServiceInterface<E>>  {
    //Class attributes, set by constructor
    //Path for our Templates
    protected String path;
    //The modelname to use in Thymeleaf and which is used in our modelFactory
    protected String modelname = "cruditem";
    //The service that the controller is making use of.
    protected S service;
    MicroServiceService msservice;

    public MSCRUDAbstractController(){}

    public MSCRUDAbstractController(String path, String modelname, S service, MicroServiceService msservice){
        this.path = "admin/"+path;
        this.service = service;
        this.modelname = modelname;
        this.msservice = msservice;
    }

    @GetMapping({"index", ""})
    public String get_root_index(Model model)
    {
        System.out.println("Index");
        model.addAttribute(modelname + "s", service.findAll());
        return path + "index";
    }

    @PostMapping({"index", ""})
    public String get_root_index(Model model, HttpSession session, @RequestParam("selectedmicroserviceid") int selectedmicroserviceid)
    {
        session.setAttribute("selectedmicroserviceid", new Integer(selectedmicroserviceid));
        model.addAttribute("selectedmicroserviceid", selectedmicroserviceid);
        model.addAttribute(modelname + "s", service.findAll());
        return path + "index";
    }

    @GetMapping("create")
    public String get_create(Model model, HttpSession session, E modelojb) {
        model.addAttribute(modelname, modelojb);
        return path + "create";
    }

    @PostMapping("create")
    public String post_create(@ModelAttribute @Valid E e, BindingResult result, HttpSession session, Model model){
        model.addAttribute(modelname, e);
        if (result.hasErrors()) {
            return path + "create";
        }
        E newe = service.create(e);
        return "redirect:/"+path+"info/" + newe.getId();
    }

    @GetMapping("/edit/{id}")
    public String get_edit(@PathVariable int id, Model model, HttpSession session)
    {
        model.addAttribute(modelname, service.findById(id));
        return path+"edit";
    }

    @PostMapping("/edit")
    public String post_edit(@ModelAttribute @Valid E e, BindingResult result, HttpSession session, Model model)
    {
        model.addAttribute(modelname, e);
        if (result.hasErrors()) {
            return path + "edit";
        }
        service.edit(e);
        return "redirect:/" + path + "info/" + e.getId();
    }

    @GetMapping("/delete/{id}")
    public String get_delete(@PathVariable int id, Model model, HttpSession session)
    {
        model.addAttribute(modelname, service.findById(id));
        return path + "delete";
    }
    @PostMapping("/delete")
    public String post_delete(@RequestParam(value="id") int id, HttpSession session)
    {
        service.delete(id);
        return "redirect:/" + path;
    }
    @GetMapping("/view/{id}")
    public String get_view(@PathVariable int id, Model model, HttpSession session)
    {
        model.addAttribute(modelname, service.findById(id));
        return path + "view";
    }

    @ModelAttribute("microservices")
    public List<MicroService> model_microservices(){
        System.out.println("model_microservices");
        for(MicroService ms : msservice.findAll()){
            System.out.println(ms.getId() + " " + ms.getName());
        }
        return msservice.findAll();
    }

    @ModelAttribute("selectedmicroserviceid")
    public int selected_microservice(HttpSession session){
        if(session.getAttribute("selectedmicroserviceid") != null){
            return ((Integer)session.getAttribute("selectedmicroserviceid")).intValue();
        }
        return 0;
    }
}
