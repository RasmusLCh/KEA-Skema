package kea.schedule.scheduleservice.services;

import kea.schedule.scheduleservice.models.Course;
import kea.schedule.scheduleservice.models.LectureItem;
import kea.schedule.scheduleservice.repositories.LectureItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        System.out.println("Edit 1");
        repo.save(lectureItem);
        System.out.println("Edit 2");
        actionservice.doAction("LectureItemService.edit", lectureItem);
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
        actionservice.doAction("LectureItemService.delete", new LectureItem(id));
    }

    @Override
    public LectureItem findById(int id) {
        Optional opt = repo.findById(id);
        if(opt.isPresent()){
            return (LectureItem)opt.get();
        }
        return null;
    }

    @Override
    public List<LectureItem> findAll() {
        return repo.findAllByOrderByPriorityDesc();
    }

    public List<LectureItem> findAllByLectureSubjectId(int lecturesubjectid){
        return repo.findAllByLecturesubjectIdOrderByPriorityDesc(lecturesubjectid);
    }
}
