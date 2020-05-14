package microservice.infrastructure.controllers.admin;

import microservice.infrastructure.models.Group;
import microservice.infrastructure.models.User;
import microservice.infrastructure.services.GroupService;
import microservice.infrastructure.services.MicroServiceService;
import microservice.infrastructure.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Handles user creation.
 * The class extends the CRUDAbstractController so only path, modelname and CRUD service is specified.
 * url access /admin/users/
 * */

@Controller
@RequestMapping("/admin/users/")
public class CRUDUserController extends CRUDAbstractController<User, UserService>{
    private GroupService groupservice;

    /**
     * @param   userservice The user service to use
     * @param   groupservice    The service that handles group requests
    * */
    @Autowired
    public CRUDUserController(UserService userservice, GroupService groupservice){
        super("users/", "user", userservice);
        this.groupservice = groupservice;
    }

    /**
     * Override get_create to be able to add groups to the Model
     * */
    @Override
    @GetMapping("create")
    public String get_create(Model model, HttpSession session, User modelojb) {
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        model.addAttribute("groups", groupservice.findAll());
        return super.get_create(model, session, modelojb);
    }

    /**
     * Override post_create to be able to add groups to the Model
     * */
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

    /**
     * Override get_edit to be able to add groups to the Model
     * */
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

    /**
     * Override post_edit to be able to add groups to the Model
     * */
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
