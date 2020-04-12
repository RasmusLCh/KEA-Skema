package kea.schedule.scheduleservice.converters.deserialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import kea.schedule.scheduleservice.models.User;

import java.util.ArrayList;
import java.util.List;

public class ListUserDeserializer extends StdConverter<List<Integer>, List<User>> {

    public List<User> convert(List<Integer> ids){
        List<User> usrs = new ArrayList();
        for(Integer id: ids){
            usrs.add(new User(id));
        }
        return usrs;
    }
}