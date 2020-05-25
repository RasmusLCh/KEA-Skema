package microservice.infrastructure.controllers.admin;

import microservice.infrastructure.models.User;
import microservice.infrastructure.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/usersearch/")
public class UserRestController {
    @Autowired
    private HttpSession session;
    private UserService userService;

    public UserRestController(UserService userservice) {
        this.userService = userservice;
    }

    @PostMapping(value = "find", consumes = "application/json")
    public ResponseEntity<List<User>> find(@RequestBody User user) {
        System.out.println(user.getDisplayname());
        List<User> users = userService.findUsersByDisplaynameStartingWith(user.getDisplayname());
        //System.out.println("Received " + umso.getMicroserviceoption() + " " + umso.getActive() + " " + umso.getUser());
        //Edit will also create the element if its not already created
        System.out.println(user.getDisplayname());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
