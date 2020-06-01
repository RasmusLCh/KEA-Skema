package microservice.infrastructure.converters.deserialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import microservice.infrastructure.models.User;

public class UserDeserializer extends StdConverter<Integer, User> {

    public User convert(Integer id){
        return new User(id.intValue());
    }
}