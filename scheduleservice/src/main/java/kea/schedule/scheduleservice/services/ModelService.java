package kea.schedule.scheduleservice.services;

import kea.schedule.scheduleservice.models.ModelInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
/**
 * Factory for Model services
 * */
@Service
public class ModelService {
    Map servicemap;

    @Autowired
    public ModelService(CourseService courseservice,
                        GroupService groupservice,
                        LectureItemService lectureitemservice,
                        LectureService lectureservice,
                        LectureSubjectService lecturesubjectservice,
                        SubjectPriorityService subjectpriorityservice,
                        UserService userservice){
        this.servicemap = new HashMap<String, CRUDServiceInterface<ModelInterface>>();
        this.servicemap.put("CourseService", courseservice);
        this.servicemap.put("Group", groupservice);
        this.servicemap.put("LectureItemService", lectureitemservice);
        this.servicemap.put("LectureService", lectureservice);
        this.servicemap.put("LectureSubjectService", lecturesubjectservice);
        this.servicemap.put("SubjectPriorityService", subjectpriorityservice);
        this.servicemap.put("User", userservice);
    }

    public CRUDServiceInterface<ModelInterface> getService(String classname){
        if(servicemap.containsKey(classname)){
            return (CRUDServiceInterface<ModelInterface>)servicemap.get(classname);
        }
        return null;
    }
}
