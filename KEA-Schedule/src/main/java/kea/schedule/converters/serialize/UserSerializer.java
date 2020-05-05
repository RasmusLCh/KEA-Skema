package kea.schedule.converters.serialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import kea.schedule.models.User;

public class UserSerializer extends StdConverter<User, Integer> {
    public Integer convert(User usr){
        if(usr == null){
            return new Integer(0);
        }
        return usr.getId();
    }
}