package kea.schedule.scheduleservice.services;

import kea.schedule.scheduleservice.models.Course;
import kea.schedule.scheduleservice.models.LectureItem;
import kea.schedule.scheduleservice.repositories.LectureItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureItemService implements CRUDServiceInterface<LectureItem> {
    private LectureItemRepo repo;
    private ActionService actionservice;
    @Autowired
    public LectureItemService(LectureItemRepo repo, ActionService actionservice){
        this.repo = repo;
        this.actionservice = actionservice;
    }

    @Override
    public LectureItem create(LectureItem lectureItem) {
        LectureItem li = repo.save(lectureItem);
        actionservice.doAction("LectureItemService.create", li);
        return li;
    }

    @Override
    public void edit(LectureItem lectureItem) {
        repo.save(lectureItem);
        actionservice.doAction("LectureItemService.edit", lectureItem);
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
        actionservice.doAction("LectureItemService.delete", new LectureItem(id));
    }

    @Override
    public LectureItem findById(int id) {
        return repo.findById(id).get();
    }

    @Override
    public List<LectureItem> findAll() {
        return repo.findAll();
    }
}
