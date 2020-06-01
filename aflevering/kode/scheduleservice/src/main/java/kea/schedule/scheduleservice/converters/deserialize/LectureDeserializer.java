package kea.schedule.scheduleservice.converters.deserialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import kea.schedule.scheduleservice.models.Lecture;

public class LectureDeserializer extends StdConverter<Integer, Lecture> {

        public Lecture convert(Integer id){
            return new Lecture(id);
        }
    }