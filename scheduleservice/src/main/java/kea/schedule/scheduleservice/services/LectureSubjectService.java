package kea.schedule.scheduleservice.services;

import kea.schedule.scheduleservice.components.MSSession;
import kea.schedule.scheduleservice.models.Lecture;
import kea.schedule.scheduleservice.models.LectureSubject;
import kea.schedule.scheduleservice.repositories.LectureSubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
public class LectureSubjectService  implements CRUDServiceInterface<LectureSubject> {
    private LectureSubjectRepo repo;
    private ActionService actionservice;
    private MSSession session;

    @Autowired
    public LectureSubjectService(LectureSubjectRepo repo, ActionService actionservice, MSSession session){
        this.repo = repo;
        this.actionservice = actionservice;
        this.session = session;
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
        Optional opt = repo.findById(id);
        if(opt.isPresent()){
            return (LectureSubject)opt.get();
        }
        return null;
    }

    @Override
    public List<LectureSubject> findAll() {
        return repo.findAllByOrderByPriorityDesc();
    }

    public List<LectureSubject> findAllByLectureId(int lectureid){
        return repo.findAllByLectureIdOrderByPriorityDesc(lectureid);
    }

    public void setSelectedLectureSubject(int lecturesubjectid, Model model){
        session.setAttribute("selectedlecturesubjectid", new Integer(lecturesubjectid));
        model.addAttribute("selectedlecturesubjectid", getSelectedLectureSubjectId());
        model.addAttribute("selectedlecturesubject", getSelectedLectureSubject());
    }

    public int getSelectedLectureSubjectId(){
        if(session.getAttribute("selectedlecturesubjectid") != null){
            return ((Integer)session.getAttribute("selectedlecturesubjectid")).intValue();
        }
        return 0;
    }

    public LectureSubject getSelectedLectureSubject(){
        if(session.getAttribute("selectedlecturesubjectid") != null && ((Integer)session.getAttribute("selectedlecturesubjectid")).intValue() > 0){
            System.out.println("Selected lecture subject is " + session.getAttribute("selectedlecturesubjectid"));
            return findById(((Integer)session.getAttribute("selectedlecturesubjectid")).intValue());
        }
        return null;
    }
}
