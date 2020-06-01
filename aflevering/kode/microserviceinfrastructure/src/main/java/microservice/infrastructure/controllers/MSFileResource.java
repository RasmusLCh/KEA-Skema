package microservice.infrastructure.controllers;

import microservice.infrastructure.models.FileResource;
import microservice.infrastructure.models.MicroService;
import microservice.infrastructure.services.AuthenticationService;
import microservice.infrastructure.services.MicroServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Access resources that microservices has uploaded to the infrastructure.
 * url /serviceresource/<MicroService Name>/<ResourceFile>
 * */

@Controller
@RequestMapping("/serviceresource/")
public class MSFileResource {
    private MicroServiceService msservice;
    private AuthenticationService authservice;

    @Autowired
    public MSFileResource(MicroServiceService msservice, AuthenticationService authservice){
        this.msservice = msservice;
        this.authservice = authservice;
    }
    /*

    /**
     * An download a file, calling with subject . extension
     * */

    @GetMapping("{servicename}/{filename}")
    public HttpEntity<byte[]> get_download(@PathVariable String servicename,
                                           @PathVariable String filename){
        MicroService ms = msservice.findByName(servicename);
        if(ms == null){
            return new HttpEntity(HttpStatus.BAD_REQUEST);
        }
        if(!authservice.hasAccess(ms)){
            return new HttpEntity(HttpStatus.FORBIDDEN);
        }
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
