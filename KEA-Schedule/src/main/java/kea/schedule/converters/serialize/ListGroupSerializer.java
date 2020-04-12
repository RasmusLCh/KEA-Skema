package kea.schedule.converters.serialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import kea.schedule.models.Group;

import java.util.ArrayList;
import java.util.List;
public class ListGroupSerializer extends StdConverter<List<Group>, List<Integer>> {

    public List<Integer> convert(List<Group> grps){
        List<Integer> ids = new ArrayList();
        for(Group grp: grps){
            ids.add(grp.getId());
        }
        return ids;
    }
}