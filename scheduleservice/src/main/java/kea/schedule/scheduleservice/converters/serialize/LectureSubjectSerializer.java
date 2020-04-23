package kea.schedule.scheduleservice.converters.serialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import kea.schedule.scheduleservice.models.LectureSubject;

public class LectureSubjectSerializer extends StdConverter<LectureSubject, Integer> {
    public Integer convert(LectureSubject ls){
        return new Integer(ls.getId());
    }
}

