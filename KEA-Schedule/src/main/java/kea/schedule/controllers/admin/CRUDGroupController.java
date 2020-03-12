package kea.schedule.controllers.admin;

import kea.schedule.moduls.Group;
import kea.schedule.services.GroupService;
import kea.schedule.services.MicroServiceService;
import kea.schedule.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/groups/")
public class CRUDGroupController  extends MSCRUDAbstractController<Group, GroupService> {
    private UserService userservice;
    @Autowired
    public CRUDGroupController(GroupService groupservice, UserService userservice,MicroServiceService msservice){
        super("groups/", "group", groupservice, msservice);
        this.userservice = userservice;
        //service = groupservice - Service is a protected variable in MSCRUDAbstractController
    }



    @Override
    @GetMapping("create")
    public String get_create(Model model, HttpSession session, Group modelojb) {
        model.addAttribute("groups", service.findAll());
        model.addAttribute("users", userservice.findAll());
        model.addAttribute(modelname, modelojb);
        return path + "create";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String get_edit(@PathVariable int id, Model model, HttpSession session)
    {
        model.addAttribute("groups", service.findAll());
        model.addAttribute("users", userservice.findAll());
        model.addAttribute(modelname, service.findById(id));
        return path+"edit";
    }
}
