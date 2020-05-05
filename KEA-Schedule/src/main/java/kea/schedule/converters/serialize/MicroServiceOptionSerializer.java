package kea.schedule.converters.serialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import kea.schedule.models.MicroServiceOption;

public class MicroServiceOptionSerializer extends StdConverter<MicroServiceOption, Integer> {
    public Integer convert(MicroServiceOption mso){
        if(mso == null){
            return new Integer(0);
        }
        return mso.getId();
    }
}