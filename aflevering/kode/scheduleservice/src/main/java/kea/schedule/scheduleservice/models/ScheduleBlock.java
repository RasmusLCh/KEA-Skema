package kea.schedule.scheduleservice.models;

import java.util.ArrayList;
import java.util.List;

public class ScheduleBlock {
    //The size dictates how many rows that the block is filling out, if its 0, the block is ignored
    int size;
    List<Lecture> lectures = new ArrayList<>();
    String message = null;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
