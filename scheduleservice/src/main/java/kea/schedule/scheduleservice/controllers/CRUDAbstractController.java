package kea.schedule.scheduleservice.controllers;


import kea.schedule.scheduleservice.models.ModelInterface;
import kea.schedule.scheduleservice.services.CRUDServiceInterface;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    protected int port;
    //Should link to the REQUESTMapping, but NOT start with a leading /
    protected String webaddr;

    public CRUDAbstractController(){}

    public CRUDAbstractController(String path, String modelname, String webaddr, S service, int port){
        this.path = path;
        this.service = service;
        this.modelname = modelname;
        this.webaddr = webaddr;
        this.port = port;
    }

    @GetMapping({"index", ""})
    public String get_root_index(Model model)
    {
        System.out.println("Root");
        model.addAttribute(modelname + "s", service.findAll());
        return path + "index";
    }

    @GetMapping("create")
    public String get_create(Model model, E modelojb) {
        model.addAttribute(modelname, modelojb);
        return path + "create";
    }

    @PostMapping("create")
    public String post_create(@ModelAttribute @Valid E e, BindingResult result, Model model){
        model.addAttribute(modelname, e);
        if (result.hasErrors()) {
            return path + "create";
        }
        E newe = service.create(e);
        return "redirect:/"+webaddr+"view/" + newe.getId() + "/";
    }

    @GetMapping("/edit/{id}")
    public String get_edit(@PathVariable int id, Model model)
    {
        model.addAttribute(modelname, service.findById(id));
        return path+"edit";
    }

    @PostMapping("/edit")
    public String post_edit(@ModelAttribute @Valid E e, BindingResult result, Model model)
    {
        model.addAttribute(modelname, e);
        if (result.hasErrors()) {
            return path + "edit";
        }
        service.edit(e);
        return "redirect:/" + webaddr + "view/" + e.getId() + "/";
    }

    @GetMapping("/delete/{id}")
    public String get_delete(@PathVariable int id, Model model)
    {
        model.addAttribute(modelname, service.findById(id));
        return path + "delete";
    }
    @PostMapping("/delete")
    public String post_delete(@RequestParam(value="id") int id)
    {
        service.delete(id);
        return "redirect:/" + webaddr + "index";
    }
    @GetMapping("/view/{id}")
    public String get_view(@PathVariable int id, Model model)
    {
        model.addAttribute(modelname, service.findById(id));
        return path + "view";
    }
}
