package kea.schedule.scheduleservice.services;

import kea.schedule.scheduleservice.models.Course;
import kea.schedule.scheduleservice.repositories.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService implements CRUDServiceInterface<Course>{
    private CourseRepo repo;
    private ActionService actionservice;
    @Autowired
    public CourseService(CourseRepo repo, ActionService actionservice){
        this.repo = repo;
        this.actionservice = actionservice;
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
}
