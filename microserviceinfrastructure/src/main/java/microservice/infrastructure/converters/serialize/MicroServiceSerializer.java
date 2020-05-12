package microservice.infrastructure.converters.serialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import microservice.infrastructure.models.MicroService;

public class MicroServiceSerializer extends StdConverter<MicroService, Integer> {
    public Integer convert(MicroService ms){
        if(ms == null){
            return new Integer(0);
        }
        return ms.getId();
    }
}