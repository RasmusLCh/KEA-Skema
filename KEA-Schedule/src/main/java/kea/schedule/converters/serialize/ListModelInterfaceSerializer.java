package kea.schedule.converters.serialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import kea.schedule.models.ModelInterface;

import java.util.ArrayList;
import java.util.List;

public class ListModelInterfaceSerializer extends StdConverter<List<ModelInterface>, List<Integer>> {

    public List<Integer> convert(List<ModelInterface> mifs){
        List<Integer> ids = new ArrayList();
        for(ModelInterface mif: mifs){
            ids.add(mif.getId());
        }
        return ids;
    }
}