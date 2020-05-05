package kea.schedule.converters.deserialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import kea.schedule.models.MicroService;
import kea.schedule.models.User;

public class MicroServiceDeserializer extends StdConverter<Integer, MicroService> {

    public MicroService convert(Integer id){
        return new MicroService(id.intValue());
    }
}