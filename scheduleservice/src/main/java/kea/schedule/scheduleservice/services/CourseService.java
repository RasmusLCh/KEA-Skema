package kea.schedule.scheduleservice.services;

import kea.schedule.scheduleservice.components.MSSession;
import kea.schedule.scheduleservice.models.Course;
import kea.schedule.scheduleservice.repositories.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService implements CRUDServiceInterface<Course>{
    private CourseRepo repo;
    private ActionService actionservice;
    private MSSession session;
    private AuthenticationService authservice;
    private UserService userservice;


    @Autowired
    public CourseService(CourseRepo repo, ActionService actionservice, MSSession session, AuthenticationService authservice, UserService userservice){
        this.repo = repo;
        this.actionservice = actionservice;
        this.session = session;
        this.authservice = authservice;
        this.userservice = userservice;
    }

    @Override
    public Course create(Course course) {
        Course c = repo.save(course);
        actionservice.doAction("CourseService.create", c);
        return c;
    }

    @Override
    public void edit(Course course) {
        repo.save(course);
        actionservice.doAction("CourseService.edit", course);
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
        actionservice.doAction("CourseService.delete", new Course(id));
    }

    @Override
    public Course findById(int id) {
        return repo.findById(id).get();
    }

    @Override
    public List<Course> findAll() {
        return repo.findAll();
    }

    /**
     * Returns all courses the current user has access too
     * */
    public List<Course> findAllByAccess(){
        List<Course> courses = repo.findAll();
        List<Course> acourses = new ArrayList<>();
        for(Course course: courses){
            if(authservice.hasAccess(course.getTeachers())){
                acourses.add(course);
            }
        }
        return acourses;
    }

    public void setSelectedcourse(int couseid, Model model){
        session.setAttribute("selectedcourseid", new Integer(couseid));
        model.addAttribute("selectedcourseid", getSelectedCourseId());
        model.addAttribute("selectedcourse", getSelectedCourse());
    }

    public int getSelectedCourseId(){
        if(session.getAttribute("selectedcourseid") != null){
            return ((Integer)session.getAttribute("selectedcourseid")).intValue();
        }
        return 0;
    }

    public Course getSelectedCourse(){
        if(session.getAttribute("selectedcourseid") != null){
            System.out.println("Selected courseid is " + session.getAttribute("selectedcourseid"));
            return findById(((Integer)session.getAttribute("selectedcourseid")).intValue());
        }
        return null;
    }
}
