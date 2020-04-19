package kea.schedule.scheduleservice.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.*;
import java.util.Map;

@Converter
public class MapMSSessionEntity implements AttributeConverter<Map<String, Object>, byte[]> {
    @Override
    public byte[] convertToDatabaseColumn(Map<String, Object> stringObjectMap) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(byteOut);
            out.writeObject(stringObjectMap);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteOut.toByteArray();
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(byte[] bytes) {
        ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
        ObjectInputStream in = null;
        Map<String, Object> data = null;
        try {
            in = new ObjectInputStream(byteIn);
            data = (Map<String, Object>) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
}
