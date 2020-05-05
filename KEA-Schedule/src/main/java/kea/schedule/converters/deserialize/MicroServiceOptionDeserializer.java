package kea.schedule.converters.deserialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import kea.schedule.models.MicroServiceOption;

public class MicroServiceOptionDeserializer extends StdConverter<Integer, MicroServiceOption> {

    public MicroServiceOption convert(Integer id){
        return new MicroServiceOption(id.intValue());
    }
}