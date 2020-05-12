package microservice.infrastructure.services;

import microservice.infrastructure.models.MicroServiceOption;
import microservice.infrastructure.repositories.MicroServiceOptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MicroServiceOptionService implements CRUDServiceInterface<MicroServiceOption> {
    private MicroServiceOptionRepo repo;
    private ActionService actionservice;

    @Autowired
    public MicroServiceOptionService(MicroServiceOptionRepo msoptionservice, ActionService actionservice){
        this.repo = msoptionservice;
        this.actionservice = actionservice;
    }

    @Override
    public MicroServiceOption create(MicroServiceOption microServiceOption) {
        MicroServiceOption mso = repo.save(microServiceOption);
        actionservice.doAction("MicroServiceOptionService.create", microServiceOption);
        return mso;
    }

    @Override
    public void edit(MicroServiceOption microServiceOption) {
        repo.save(microServiceOption);
        actionservice.doAction("MicroServiceOptionService.edit", microServiceOption);
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
        actionservice.doAction("MicroServiceOptionService.delete", new MicroServiceOption(id));
    }

    @Override
    public MicroServiceOption findById(int id) {
        Optional optfr = repo.findById(id);
        if(optfr.isPresent()){
            return (MicroServiceOption) optfr.get();
        }
        return null;
    }

    @Override
    public List<MicroServiceOption> findAll() {
        return repo.findAll();
    }

    @Override
    public List<MicroServiceOption> findAll(int microserviceid) {
        return repo.findAllByMicroserviceId(microserviceid);
    }

    public MicroServiceOption findByName(String name){
        return repo.findByName(name);
    }
}
