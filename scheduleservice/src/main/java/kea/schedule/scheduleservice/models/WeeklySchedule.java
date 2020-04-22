package kea.schedule.scheduleservice.models;

import java.util.List;

public class WeeklySchedule {
    int weeknumber;
    int year;

    DailyScheduleHours dsh;
    List<DailySchedule> dailyschedule;
}
