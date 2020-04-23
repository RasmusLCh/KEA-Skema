package kea.schedule.scheduleservice.converters.deserialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import kea.schedule.scheduleservice.models.Course;

public class CourseDeserializer extends StdConverter<Integer, Course> {

        public Course convert(Integer id){
            return new Course(id);
        }
    }