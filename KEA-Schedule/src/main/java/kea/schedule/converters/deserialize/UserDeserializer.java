package kea.schedule.converters.deserialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import kea.schedule.models.User;

public class UserDeserializer extends StdConverter<Integer, User> {

    public User convert(Integer id){
        return new User(id.intValue());
    }
}