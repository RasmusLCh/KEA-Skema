package kea.schedule.scheduleservice.models;

import java.util.ArrayList;
import java.util.List;

public class ScheduleWeekly {
    int weeknumber;
    int year;

    ScheduleTimes scheduletimes;
    List<ScheduleDaily> scheduledailies = new ArrayList<>();


    public int getWeeknumber() {
        return weeknumber;
    }

    public void setWeeknumber(int weeknumber) {
        this.weeknumber = weeknumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ScheduleTimes getScheduletimes() {
        return scheduletimes;
    }

    public void setScheduletimes(ScheduleTimes scheduletimes) {
        this.scheduletimes = scheduletimes;
    }

    public List<ScheduleDaily> getScheduledailies() {
        return scheduledailies;
    }

    public void setScheduledailies(List<ScheduleDaily> scheduledailies) {
        this.scheduledailies = scheduledailies;
    }
}
