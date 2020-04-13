package kea.schedule.services;

import kea.schedule.models.FileResource;
import kea.schedule.models.MicroService;
import kea.schedule.repositories.FileResourceRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FileResourceService implements CRUDServiceInterface<FileResource> {
    private FileResourceRepo repo;
    private ActionService actionservice;

    public FileResourceService(FileResourceRepo repo, ActionService actionservice){
        this.repo = repo;
        this.actionservice = actionservice;
    }


    public FileResource bind(MultipartFile multipartfile, MicroService ms) throws IOException {
        FileResource fileresource = new FileResource();
        byte[] bytes = multipartfile.getBytes();
        String[] splitter = multipartfile.getContentType().split("/");
        fileresource.setFilename(multipartfile.getOriginalFilename());
        fileresource.setType(splitter[0]);
        fileresource.setExtension(splitter[1]);
        fileresource.setData(bytes);
        System.out.println("Bind microservice" + ms.getName());
        fileresource.setMicroservice(ms);
        return create(fileresource);
    }

    @Override
    public FileResource create(FileResource fileResource) {
        FileResource newfileresource = repo.save(fileResource);
        actionservice.doAction("FileResourceService.create", newfileresource);
        return newfileresource;
    }

    @Override
    /**
     * When editing, it is not possible to update the data. For this to happen delete the FileResource and create a new one.
     * */
    public void edit(FileResource fileResource) {
        FileResource fr = findById(fileResource.getId());
        if(fr != null){
            fileResource.setData(fr.getData());
            fileResource.setMicroservice(fr.getMicroservice());
            repo.save(fileResource);
            actionservice.doAction("FileResourceService.edit", fileResource);
        }
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
        actionservice.doAction("FileResourceService.delete", new FileResource(id));
    }

    @Override
    public FileResource findById(int id) {
        Optional optfr = repo.findById(id);
        if(optfr.isPresent()){
            return (FileResource) optfr.get();
        }
        return null;
    }

    @Override
    public List<FileResource> findAll() {
        return repo.findAll();
    }

    @Override
    public List<FileResource> findAll(int msid) {
        return repo.findAllByMicroserviceId(msid);
    }
}
