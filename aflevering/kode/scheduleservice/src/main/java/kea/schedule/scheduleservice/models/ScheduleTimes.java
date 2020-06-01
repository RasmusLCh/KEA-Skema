package kea.schedule.scheduleservice.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleTimes {
    @DateTimeFormat(pattern = "HH:mm")
    private List<LocalDateTime> times = new ArrayList<>();

    public ScheduleTimes(){}

    /**
     * This is basicly cloning it
     * */
    public ScheduleTimes(ScheduleTimes st){
        this.setTimes(new ArrayList(st.getTimes()));
    }

    public List<LocalDateTime> getTimes() {
        return times;
    }

    public void setTimes(List<LocalDateTime> times) {
        this.times = times;
    }
}
