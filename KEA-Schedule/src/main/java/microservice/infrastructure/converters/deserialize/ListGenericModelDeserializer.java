package microservice.infrastructure.converters.deserialize;

import com.fasterxml.jackson.databind.util.StdConverter;
import microservice.infrastructure.models.GenericModel;

import java.util.ArrayList;
import java.util.List;

public class ListGenericModelDeserializer extends StdConverter<List<Integer>, List<GenericModel>> {

    public List<GenericModel> convert(List<Integer> ids){
        List<GenericModel> mis = new ArrayList();
        for(Integer id: ids){
            mis.add(new GenericModel(id));
        }
        return mis;
    }
}