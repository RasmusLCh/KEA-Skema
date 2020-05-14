package microservice.infrastructure.controllers.admin;

import microservice.infrastructure.models.FileResource;
import microservice.infrastructure.models.MicroService;
import microservice.infrastructure.services.FileResourceService;
import microservice.infrastructure.services.MicroServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * The class extends the MSCRUDAbstractController so only path, modename and CRUD service and msservice is specified.
 * Since it is not possible to overrride postmapping, a new mapping called bind is used.
 * */
@Controller
@RequestMapping("/admin/fileresources/")
public class CRUDFileResourceController extends MSCRUDAbstractController<FileResource, FileResourceService> {
    @Autowired
    public CRUDFileResourceController(FileResourceService fileresource, MicroServiceService msservice){
        super("fileresources/", "fileresource", fileresource, msservice);
    }

    /**
     * Its not possible to override a post mapping, so we use another name for create
     */
    @PostMapping("bind")
    public String post_bind(@RequestParam("multipartfile") MultipartFile multipartfile, @RequestParam("microservice.id") int msid, HttpSession session, Model model){
        if(!authservice.isAdmin()){
            return "forbidden";
        }
        if(session.getAttribute("selectedmicroserviceid") != null){
            MicroService ms = msservice.findById(msid);
            if(ms != null){
                FileResource newe = null;
                try {
                    newe = service.bind(multipartfile, ms);
                    model.addAttribute(modelname, newe);
                    return "redirect:/"+path+"view/" + newe.getId() + "/";
                } catch (IOException e) {
                    //result.rejectValue("multipartfile","The uploaded file is invalid");
                }
            }
        }
        /*
        if (result.hasErrors()) {
            return path + "create";
        }

         */
        return "error";
    }
}
