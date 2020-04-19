package kea.schedule.scheduleservice.services;

import kea.schedule.scheduleservice.models.MSSessionEntity;
import kea.schedule.scheduleservice.repositories.MSSessionEntityRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MSSessionEntityService implements CRUDServiceInterface<MSSessionEntity>{
    private MSSessionEntityRepo repo;
    private ActionService actionservice;
    public MSSessionEntityService(MSSessionEntityRepo repo, ActionService actionservice){
        this.repo = repo;
        this.actionservice = actionservice;
    }

    @Override
    public MSSessionEntity create(MSSessionEntity msSessionEntity) {
        MSSessionEntity se = repo.save(msSessionEntity);
        actionservice.doAction("MSSessionEntityService.create", se);
        return se;
    }

    @Override
    public void edit(MSSessionEntity msSessionEntity) {
        MSSessionEntity se = repo.save(msSessionEntity);
        actionservice.doAction("MSSessionEntityService.edit", se);
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
        actionservice.doAction("MSSessionEntityService.delete", new MSSessionEntity(id));
    }

    @Override
    public MSSessionEntity findById(int id) {
        return repo.findById(id).get();
    }

    @Override
    public List<MSSessionEntity> findAll() {
        return repo.findAll();
    }

    /**
     * Returns all Courses that's available to the user
     * */
    public List<MSSessionEntity> findAllAvailable(int userid) {
        return repo.findAll();
    }

    public MSSessionEntity findByUserId(int userid){
        return repo.findByUserid(userid);
    }
}
