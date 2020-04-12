package kea.schedule.services;

import kea.schedule.models.FileResource;
import kea.schedule.repositories.FileResourceRepo;
import org.springframework.stereotype.Service;

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

    @Override
    public FileResource create(FileResource fileResource) {
        FileResource newfileresource = repo.save(fileResource);
        actionservice.doAction("FileResourceService.create", newfileresource);
        return newfileresource;
    }

    @Override
    public void edit(FileResource fileResource) {
        repo.save(fileResource);
        actionservice.doAction("FileResourceService.edit", fileResource);
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
}
