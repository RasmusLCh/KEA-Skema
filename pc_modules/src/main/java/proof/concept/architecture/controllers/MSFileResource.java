package proof.concept.architecture.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import proof.concept.architecture.modules.FileResource;
import proof.concept.architecture.services.MSService;

@Controller
@RequestMapping("/serviceresource/")
public class MSFileResource {

    public MSService msservice;

    @Autowired
    public MSFileResource(MSService msservice){
        this.msservice = msservice;
    }
    /*

    /**
     * An administrator can download a file, calling with subject . extension
     * */

    @GetMapping("{servicename}/{filename}")
    public HttpEntity<byte[]> get_download(@PathVariable String servicename,
                                           @PathVariable String filename){
        FileResource file = msservice.findFileResourceByMSAndFilename(servicename,filename);
        if(file != null){
            System.out.println("File != null");
            HttpHeaders header = new HttpHeaders();
            header.set("Content-Type", file.getType()+"/"+file.getExtension());
            header.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=" + file.getFilename());
            header.setContentLength(file.getData().length);
            return new HttpEntity<>(file.getData(), header);
        }
        System.out.println("File == null");
        return new HttpEntity(HttpStatus.BAD_REQUEST);
    }
}
