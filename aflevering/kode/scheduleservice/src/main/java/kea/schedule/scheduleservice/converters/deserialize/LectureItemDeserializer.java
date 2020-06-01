package kea.schedule.scheduleservice.converters.deserialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import kea.schedule.scheduleservice.models.LectureItem;

public class LectureItemDeserializer extends StdConverter<Integer, LectureItem> {

        public LectureItem convert(Integer id){
            return new LectureItem(id);
        }
    }