package kea.schedule.scheduleservice.controllers;


import kea.schedule.scheduleservice.models.ModelInterface;
import kea.schedule.scheduleservice.services.CRUDServiceInterface;
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
    protected int port;

    public CRUDAbstractController(){}

    public CRUDAbstractController(String path, String modelname, S service, int port){
        this.path = path;
        this.service = service;
        this.modelname = modelname;
        this.port = port;
    }

    @GetMapping({"index", ""})
    public String get_root_index(Model model, HttpSession session)
    {
        System.out.println("Root");
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
        return "redirect:/"+path+"view/" + newe.getId() + "/";
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
        return "redirect:/" + path + "view/" + e.getId() + "/";
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
}
