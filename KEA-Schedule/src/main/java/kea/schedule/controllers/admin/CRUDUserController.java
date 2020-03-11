package kea.schedule.controllers.admin;

import kea.schedule.moduls.User;
import kea.schedule.services.MicroServiceService;
import kea.schedule.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/users/")
public class CRUDUserController extends MSCRUDAbstractController<User, UserService>{
    @Autowired
    public CRUDUserController(UserService userservice, MicroServiceService msservice){
        super("users/", "user", userservice, msservice);
    }
}
