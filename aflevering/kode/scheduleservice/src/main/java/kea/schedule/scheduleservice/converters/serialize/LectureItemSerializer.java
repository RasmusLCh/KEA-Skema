package kea.schedule.scheduleservice.converters.serialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import kea.schedule.scheduleservice.models.LectureItem;

public class LectureItemSerializer extends StdConverter<LectureItem, Integer> {
    public Integer convert(LectureItem ls){
        return new Integer(ls.getId());
    }
}

