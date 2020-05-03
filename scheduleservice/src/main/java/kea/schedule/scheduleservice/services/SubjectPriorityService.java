package kea.schedule.scheduleservice.services;

import kea.schedule.scheduleservice.models.LectureSubject;
import kea.schedule.scheduleservice.models.SubjectPriority;
import kea.schedule.scheduleservice.repositories.LectureSubjectRepo;
import kea.schedule.scheduleservice.repositories.SubjectPriorityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectPriorityService implements CRUDServiceInterface<SubjectPriority>{
    private SubjectPriorityRepo repo;
    private ActionService actionservice;
    private LectureSubjectRepo lecturesubjectrepo;
    @Autowired
    public SubjectPriorityService(SubjectPriorityRepo repo, ActionService actionservice, LectureSubjectRepo lecturesubjectrepo){
        this.repo = repo;
        this.actionservice = actionservice;
        this.lecturesubjectrepo = lecturesubjectrepo;
    }

    @Override
    public SubjectPriority create(SubjectPriority subjectPriority) {
        SubjectPriority sp = repo.save(subjectPriority);
        actionservice.doAction("SubjectPriorityService.create", sp);
        updateLectureSubjectPriority(sp);
        return sp;
    }

    @Override
    public void edit(SubjectPriority subjectPriority) {
        repo.save(subjectPriority);
        updateLectureSubjectPriority(subjectPriority);
        actionservice.doAction("SubjectPriorityService.edit", subjectPriority);
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
        actionservice.doAction("SubjectPriorityService.delete", new SubjectPriority(id));

    }

    public SubjectPriority findBySubject(String subject){
        SubjectPriority sp = repo.findBySubject(subject);
        if(sp == null){
            sp = new SubjectPriority(0, "", 50);
        }
        return sp;
    }

    @Override
    public SubjectPriority findById(int id) {
        Optional opt = repo.findById(id);
        if(opt.isPresent()){
            return (SubjectPriority)opt.get();
        }
        return null;
    }

    @Override
    public List<SubjectPriority> findAll() {
        return repo.findAll();
    }

    private void updateLectureSubjectPriority(SubjectPriority sp){
        lecturesubjectrepo.updatePrioBasedOnSubjectPriority(sp.getSubject(), sp.getPriority());
    }
}
