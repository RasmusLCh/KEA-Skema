package kea.schedule.scheduleservice.services;

import kea.schedule.scheduleservice.models.LectureSubject;
import kea.schedule.scheduleservice.repositories.LectureSubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureSubjectService  implements CRUDServiceInterface<LectureSubject> {
    private LectureSubjectRepo repo;
    private ActionService actionservice;
    @Autowired
    public LectureSubjectService(LectureSubjectRepo repo, ActionService actionservice){
        this.repo = repo;
        this.actionservice = actionservice;
    }

    @Override
    public LectureSubject create(LectureSubject lectureSubject) {
        LectureSubject ls = repo.save(lectureSubject);
        actionservice.doAction("LectureSubjectService.create", lectureSubject);
        return ls;
    }

    @Override
    public void edit(LectureSubject lectureSubject) {
        repo.save(lectureSubject);
        actionservice.doAction("LectureSubjectService.edit", lectureSubject);
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
        actionservice.doAction("LectureSubjectService.delete", new LectureSubject(id));
    }

    @Override
    public LectureSubject findById(int id) {
        return repo.findById(id).get();
    }

    @Override
    public List<LectureSubject> findAll() {
        return repo.findAll();
    }

    public List<LectureSubject> findAllByLectureId(int lectureid){
        return repo.findAllByLectureId(lectureid);
    }
}
