package microservice.infrastructure.converters.serialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import microservice.infrastructure.models.MicroServiceOption;

public class MicroServiceOptionSerializer extends StdConverter<MicroServiceOption, Integer> {
    public Integer convert(MicroServiceOption mso){
        if(mso == null){
            return new Integer(0);
        }
        return mso.getId();
    }
}