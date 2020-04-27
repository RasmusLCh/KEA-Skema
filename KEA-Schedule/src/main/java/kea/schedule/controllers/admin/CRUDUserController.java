package kea.schedule.controllers.admin;

import kea.schedule.models.Group;
import kea.schedule.models.User;
import kea.schedule.services.GroupService;
import kea.schedule.services.MicroServiceService;
import kea.schedule.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin/users/")
public class CRUDUserController extends CRUDAbstractController<User, UserService>{
    private GroupService groupservice;

    @Autowired
    public CRUDUserController(UserService userservice, MicroServiceService msservice, GroupService groupservice){
        super("users/", "user", userservice);
        this.groupservice = groupservice;
    }

    @Override
    @GetMapping("create")
    public String get_create(Model model, HttpSession session, User modelojb) {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute("groups", groupservice.findAll());
        return super.get_create(model, session, modelojb);
    }

    @Override
    @PostMapping("create")
    public String post_create(@ModelAttribute @Valid User e, BindingResult result, HttpSession session, Model model){
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute(modelname, e);
        if (result.hasErrors()) {
            model.addAttribute("groups", groupservice.findAll());
            return path + "create";
        }
        for(Group grp : e.getGroups()){
            System.out.println("Groups in user " + grp.getName());
        }
        User newe = service.create(e);
        return "redirect:/"+path+"view/" + newe.getId() + "/";
    }


    @Override
    @GetMapping("/edit/{id}")
    public String get_edit(@PathVariable int id, Model model, HttpSession session)
    {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute("groups", groupservice.findAll());
        return super.get_edit(id, model, session);
    }

    @Override
    @PostMapping("/edit")
    public String post_edit(@ModelAttribute @Valid User e, BindingResult result, HttpSession session, Model model)
    {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute(modelname, e);
        if (result.hasErrors()) {
            model.addAttribute("groups", groupservice.findAll());
            return path + "edit";
        }
        service.edit(e);
        return "redirect:/" + path + "view/" + e.getId() + "/";
    }
}
