package microservice.infrastructure.converters.serialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import microservice.infrastructure.models.User;

import java.util.ArrayList;
import java.util.List;

public class ListUserSerializer extends StdConverter<List<User>, List<Integer>> {

    public List<Integer> convert(List<User> usrs){
        List<Integer> ids = new ArrayList();
        for(User usr: usrs){
            ids.add(usr.getId());
        }
        return ids;
    }
}