package microservice.infrastructure.controllers.admin;

import microservice.infrastructure.models.ModelInterface;
import microservice.infrastructure.services.AuthenticationService;
import microservice.infrastructure.services.CRUDServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Abstract implementation of a CRUD controller.
 *
 * This abstract implementation expect a POJO object to be created.
 * The POJO object must implement ModelInterface
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

    /**
     * Sets the path for the html files
     * Set the model name
     * Set the service used
     * */
    public CRUDAbstractController(String path, String modelname, S service){
        this.path = "admin/"+path;
        this.service = service;
        this.modelname = modelname;
    }
    /**
     * Sets the path for the html files
     * Set the model name
     * Set the service used
     * Set the authentication service to use
     * */
    public CRUDAbstractController(String path, String modelname, S service, AuthenticationService authservice){
        this.path = "admin/"+path;
        this.service = service;
        this.modelname = modelname;
        this.authservice = authservice;
    }

    /**
     * Returns the index page, showing all models
     * */
    @GetMapping({"index", ""})
    public String get_root_index(Model model, HttpSession session)
    {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute(modelname + "s", service.findAll());
        return path + "index";
    }

    /**
     * Returns the index page, showing all models
     * */
    @PostMapping({"index", ""})
    public String get_root_index(Model model, HttpSession session, @RequestParam("selectedmicroserviceid") int selectedmicroserviceid)
    {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute(modelname + "s", service.findAll());
        return path + "index";
    }

    /**
     * Returns a create page, where the admin can set object fields
     * */
    @GetMapping("create")
    public String get_create(Model model, HttpSession session, E modelojb) {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute(modelname, modelojb);
        return path + "create";
    }
    /**
     * Save the model and redirects to view page
     * */
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

    /**
     * Returns a edit page, where the admin can set object fields
     * */
    @GetMapping("/edit/{id}")
    public String get_edit(@PathVariable int id, Model model, HttpSession session)
    {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute(modelname, service.findById(id));
        return path+"edit";
    }

    /**
     * The model with the id specified is updated
     * */
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

    /**
     * Returns a view page, where the admin can see information about the model that the admin is about to delete, and can move forward in the deletion process
     * */
    @GetMapping("/delete/{id}")
    public String get_delete(@PathVariable int id, Model model, HttpSession session)
    {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute(modelname, service.findById(id));
        return path + "delete";
    }

    /**
     * The model with the id specified is deleted
     * */
    @PostMapping("/delete")
    public String post_delete(@RequestParam(value="id") int id, HttpSession session)
    {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        service.delete(id);
        return "redirect:/" + path;
    }

    /**
     * the view page is returned, where information about the model is shown.
     * */
    @GetMapping("/view/{id}")
    public String get_view(@PathVariable int id, Model model)
    {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute(modelname, service.findById(id));
        return path + "view";
    }
}
