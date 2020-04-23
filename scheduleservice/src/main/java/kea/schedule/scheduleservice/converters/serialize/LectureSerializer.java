package kea.schedule.scheduleservice.converters.serialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import kea.schedule.scheduleservice.models.Lecture;

public class LectureSerializer extends StdConverter<Lecture, Integer> {
    public Integer convert(Lecture ls){
        return new Integer(ls.getId());
    }
}

