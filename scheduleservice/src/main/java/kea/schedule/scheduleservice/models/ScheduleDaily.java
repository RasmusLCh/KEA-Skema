package kea.schedule.scheduleservice.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This model is pure used for viewing data
 * */
public class ScheduleDaily {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;
    private ScheduleTimes scheduletimes;
    private List<ScheduleBlock> scheduleblocks = new ArrayList<>();

    public ScheduleDaily(){

    }

    public ScheduleDaily(LocalDateTime date, ScheduleTimes schedulehours, List<ScheduleBlock> scheduleblocks){
        this.date = date;
        this.scheduletimes = scheduletimes;
        this.scheduleblocks = scheduleblocks;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public ScheduleTimes getScheduletimes() {
        return scheduletimes;
    }

    public void setScheduletimes(ScheduleTimes schedulehours) {
        this.scheduletimes = schedulehours;
    }

    public List<ScheduleBlock> getScheduleblocks() {
        return scheduleblocks;
    }

    public void setScheduleblocks(List<ScheduleBlock> scheduleblocks) {
        this.scheduleblocks = scheduleblocks;
    }
}
