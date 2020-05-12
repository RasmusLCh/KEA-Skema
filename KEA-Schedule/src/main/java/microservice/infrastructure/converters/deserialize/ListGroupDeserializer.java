package microservice.infrastructure.converters.deserialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import microservice.infrastructure.models.Group;

import java.util.ArrayList;
import java.util.List;

public class ListGroupDeserializer extends StdConverter<List<Integer>, List<Group>> {
    public List<Group> convert(List<Integer> ids){
        List<Group> grps = new ArrayList();
        for(Integer id: ids){
            grps.add(new Group(id));
        }
        return grps;
    }
}