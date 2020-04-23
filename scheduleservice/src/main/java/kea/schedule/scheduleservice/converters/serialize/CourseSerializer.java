package kea.schedule.scheduleservice.converters.serialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import kea.schedule.scheduleservice.models.Course;

public class CourseSerializer extends StdConverter<Course, Integer> {
    public Integer convert(Course ls){
        return new Integer(ls.getId());
    }
}

