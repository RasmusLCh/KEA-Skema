package kea.schedule.scheduleservice.services;

import kea.schedule.scheduleservice.components.MSSession;
import kea.schedule.scheduleservice.models.Lecture;
import kea.schedule.scheduleservice.models.LectureItem;
import kea.schedule.scheduleservice.repositories.LectureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
public class LectureService implements CRUDServiceInterface<Lecture>{
    private LectureRepo repo;
    private ActionService actionservice;
    private MSSession session;

    @Autowired
    public LectureService(LectureRepo repo, ActionService actionservice, MSSession session){
        this.repo = repo;
        this.actionservice = actionservice;
        this.session = session;
    }

    @Override
    public Lecture create(Lecture lecture) {
        actionservice.doAction("EARLY LectureService.create", lecture);
        Lecture l = repo.save(lecture);
        actionservice.doAction("LectureService.create", l);
        return l;
    }

    @Override
    public void edit(Lecture lecture) {
        repo.save(lecture);
        actionservice.doAction("LectureService.edit", lecture);
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
        actionservice.doAction("LectureService.delete", new Lecture(id));
    }

    @Override
    public Lecture findById(int id) {
        Optional opt = repo.findById(id);
        if(opt.isPresent()){
            return (Lecture)opt.get();
        }
        return null;
    }

    @Override
    public List<Lecture> findAll() {
        return repo.findAll();
    }

    public List<Lecture> findAllByCourseId(int courseid) {
        return repo.findAllByCourseIdOrderByStartdatetime(courseid);
    }

    public void setSelectedLecture(int lectureid, Model model){
        session.setAttribute("selectedlectureid", new Integer(lectureid));
        model.addAttribute("selectedlectureid", getSelectedLectureId());
        model.addAttribute("selectedlecture", getSelectedLecture());
    }

    public int getSelectedLectureId(){
        if(session.getAttribute("selectedlectureid") != null){
            return ((Integer)session.getAttribute("selectedlectureid")).intValue();
        }
        return 0;
    }

    public Lecture getSelectedLecture(){
        if(session.getAttribute("selectedlectureid") != null && ((Integer)session.getAttribute("selectedlectureid")).intValue() > 0){
            System.out.println("Selected lecture is " + session.getAttribute("selectedlectureid"));
            return findById(((Integer)session.getAttribute("selectedlectureid")).intValue());
        }
        return null;
    }
}
