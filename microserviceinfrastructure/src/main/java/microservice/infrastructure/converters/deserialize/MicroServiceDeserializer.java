package microservice.infrastructure.converters.deserialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import microservice.infrastructure.models.MicroService;

public class MicroServiceDeserializer extends StdConverter<Integer, MicroService> {

    public MicroService convert(Integer id){
        return new MicroService(id.intValue());
    }
}