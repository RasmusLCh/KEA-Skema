package kea.schedule.scheduleservice.converters.deserialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import kea.schedule.scheduleservice.models.LectureSubject;

public class LectureSubjectDeserializer extends StdConverter<Integer, LectureSubject> {

        public LectureSubject convert(Integer id){
            return new LectureSubject(id);
        }
    }