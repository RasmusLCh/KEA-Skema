package kea.schedule.controllers.admin;

import kea.schedule.models.MicroService;
import kea.schedule.models.MicroServiceElement;
import kea.schedule.models.ModelInterface;
import kea.schedule.services.AuthenticationService;
import kea.schedule.services.CRUDServiceInterface;
import kea.schedule.services.MicroServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract implementation of a CRUD controller.
 *
 * This abstract implementation expect a POJO object to be created.
 * The POJO object must implement ModelInterface
 * The POJO can get simple datatypes set from the MSCRUDAbstractController.
 *
 * */

public abstract class CRUDAbstractController<E extends ModelInterface, S extends CRUDServiceInterface<E>>  {
    //Class attributes, set by constructor
    //Path for our Templates
    protected String path;
    //The modelname to use in Thymeleaf and which is used in our modelFactory
    protected String modelname = "cruditem";
    //The service that the controller is making use of.
    protected S service;
    @Autowired
    protected AuthenticationService authservice;

    public CRUDAbstractController(){}

    public CRUDAbstractController(String path, String modelname, S service){
        this.path = "admin/"+path;
        this.service = service;
        this.modelname = modelname;
    }

    public CRUDAbstractController(String path, String modelname, S service, AuthenticationService authservice){
        this.path = "admin/"+path;
        this.service = service;
        this.modelname = modelname;
        this.authservice = authservice;
    }


    @GetMapping({"index", ""})
    public String get_root_index(Model model, HttpSession session)
    {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute(modelname + "s", service.findAll());
        return path + "index";
    }

    @PostMapping({"index", ""})
    public String get_root_index(Model model, HttpSession session, @RequestParam("selectedmicroserviceid") int selectedmicroserviceid)
    {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute(modelname + "s", service.findAll());
        return path + "index";
    }

    @GetMapping("create")
    public String get_create(Model model, HttpSession session, E modelojb) {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute(modelname, modelojb);
        return path + "create";
    }

    @PostMapping("create")
    public String post_create(@ModelAttribute @Valid E e, BindingResult result, HttpSession session, Model model){
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute(modelname, e);
        if (result.hasErrors()) {
            return path + "create";
        }
        E newe = service.create(e);
        return "redirect:/"+path+"view/" + newe.getId() + "/";
    }

    @GetMapping("/edit/{id}")
    public String get_edit(@PathVariable int id, Model model, HttpSession session)
    {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute(modelname, service.findById(id));
        return path+"edit";
    }

    @PostMapping("/edit")
    public String post_edit(@ModelAttribute @Valid E e, BindingResult result, HttpSession session, Model model)
    {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute(modelname, e);
        if (result.hasErrors()) {
            return path + "edit";
        }
        service.edit(e);
        return "redirect:/" + path + "view/" + e.getId() + "/";
    }

    @GetMapping("/delete/{id}")
    public String get_delete(@PathVariable int id, Model model, HttpSession session)
    {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute(modelname, service.findById(id));
        return path + "delete";
    }
    @PostMapping("/delete")
    public String post_delete(@RequestParam(value="id") int id, HttpSession session)
    {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        service.delete(id);
        return "redirect:/" + path;
    }
    @GetMapping("/view/{id}")
    public String get_view(@PathVariable int id, Model model, HttpSession session)
    {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute(modelname, service.findById(id));
        return path + "view";
    }
}
