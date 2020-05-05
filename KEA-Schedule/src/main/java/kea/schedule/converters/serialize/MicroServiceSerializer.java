package kea.schedule.converters.serialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import kea.schedule.models.MicroService;
import kea.schedule.models.User;

public class MicroServiceSerializer extends StdConverter<MicroService, Integer> {
    public Integer convert(MicroService ms){
        if(ms == null){
            return new Integer(0);
        }
        return ms.getId();
    }
}