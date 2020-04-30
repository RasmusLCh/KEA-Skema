package kea.schedule.scheduleservice.services;

import kea.schedule.scheduleservice.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ScheduleService {
    private UserService userservice;
    private CourseService courseservice;

    @Autowired
    public ScheduleService(UserService userservice, CourseService courseservice){
        this.userservice = userservice;
        this.courseservice = courseservice;
    }

    public List<ScheduleWeekly> getSchedulesWeekly(LocalDateTime start, LocalDateTime end){
        List<Lecture> lectures = getLectures(start, end);
        ScheduleTimes scheduletimes = getScheduleTimes();
        List<ScheduleWeekly> schedulesweekly = new ArrayList<>();
        LocalDate runningdate = start.toLocalDate();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        //Split the start and end up into weeks
        int weeknum;
        int dayofweek;
        ScheduleDaily sd;
        List<ScheduleDaily> sds;
        //Init sw if we dont start on a monday
        ScheduleWeekly sw = new ScheduleWeekly();
        boolean first = true;
        while(runningdate.compareTo(end.toLocalDate()) <= 0){
            weeknum = runningdate.get(weekFields.weekOfWeekBasedYear());
            dayofweek = runningdate.get(weekFields.dayOfWeek());
            if(dayofweek == 1 || first){
                sw = new ScheduleWeekly();
                sw.setWeeknumber(weeknum);
                sw.setScheduletimes(scheduletimes);
                if(first){
                    //If we dont start on a monday, we add empty ScheduleDaily
                    //Starting by generating dummy blocks
                    List<ScheduleBlock> sbs = new ArrayList<>();
                    for(int i = 0; i < scheduletimes.getTimes().size() * 4; i++){
                        ScheduleBlock sb = new ScheduleBlock();
                        sb.setSize(1);
                        sb.setMessage("Test");
                        sbs.add(sb);
                    }

                    for(int i = 1; i < dayofweek; i++){
                        ScheduleDaily tsd = new ScheduleDaily();
                        tsd.setScheduleblocks(sbs);
                        sw.getScheduledailies().add(tsd);
                    }
                }
                first = false;
            }
            if(dayofweek < 6){
                sds = getSchedulesDaily(runningdate.atStartOfDay(), runningdate.atTime(23, 59), scheduletimes, lectures);
                if(sds != null && sds.size() == 1){
                    sd = sds.get(0);
                    sw.getScheduledailies().add(sd);
                }
                else{
                    System.out.println("No scheduledaily returned, this should never happen!");
                    sd = null;
                }
            }
            if(dayofweek == 7){
                //save
                schedulesweekly.add(sw);
                sw = null;
            }

            System.out.println(weeknum + " " + dayofweek);

            runningdate = runningdate.plusDays(1);
        }
        if(sw != null){
            schedulesweekly.add(sw);
        }
        schedulesweekly.add(sw);
        System.out.println("Lectures returned " + lectures.size());
        return schedulesweekly;
    }

    public List<ScheduleDaily> getSchedulesDaily(LocalDateTime start, LocalDateTime end){
        return getSchedulesDaily(start, end, getScheduleTimes(), getLectures(start, end));
    }

    public List<ScheduleDaily> getSchedulesDaily(LocalDateTime start, LocalDateTime end, ScheduleTimes schedulehours, List<Lecture> lectures){
        List<ScheduleDaily> sds = new ArrayList<>();
        ScheduleDaily sd = new ScheduleDaily();
        sd.setScheduletimes(schedulehours);
        List<ScheduleBlock> sbs = new ArrayList<>();
        List<Lecture> tlectures = new ArrayList();
        //Find lectures that are happening on this day
        for(Lecture lecture : lectures){
            if(start.toLocalDate().compareTo(lecture.getStartdatetime().toLocalDate()) == 0){
                tlectures.add(lecture);
            }
        }
        //Contains how many ScheduleBlocks we are gonna skip (by setting the size to 0)
        int skipnum = 0;
        LocalDateTime runningstart = start.toLocalDate().atStartOfDay().plusHours(8);
        LocalDateTime runningend = start.toLocalDate().atStartOfDay().plusHours(8).plusMinutes(15); //8 since the schedule starts at 8
        boolean first = true;
        for(int i = 0; i < schedulehours.getTimes().size() * 4; i++){
            ScheduleBlock sb = new ScheduleBlock();
            if(skipnum > 0){
                sb.setSize(0);
                skipnum--;
            }
            else{
                sb.setSize(1);
            }

            //Try to find lectures on the specific day
            for(Lecture lecture : tlectures){
                System.out.println(
                    "Lecture compare " +
                            runningstart.compareTo(lecture.getStartdatetime()) + " " +
                            runningend.compareTo(lecture.getEnddatetime()) +" " +
                                    runningstart.toString() +" " +
                                            lecture.getStartdatetime().toString() +" " +
                                            runningend.toString() +" " +
                                            lecture.getEnddatetime().toString());
                if(runningstart.compareTo(lecture.getStartdatetime()) >= 0 && runningend.compareTo(lecture.getEnddatetime()) < 0){
                    //Found a lecture
                    long secondsdif = lecture.getEnddatetime().toEpochSecond(ZoneOffset.ofHours(1)) - lecture.getStartdatetime().toEpochSecond(ZoneOffset.ofHours(1));
                    int size = (int)Math.ceil((secondsdif / 60) / 15);
                    System.out.println("Size = " + size);
                    sb.getLectures().add(lecture);
                    sb.setSize(size);
                    skipnum = size - 1;
                    System.out.println("ADDED LECTURE TO BLOCK!!!!" + lecture.getLocation());
                    //sb.setMessage("Active size = " + size);
                }
            }
            //Lets remove the object, so we dont added them to the schedule again
            for(Lecture lecture: sb.getLectures()){
                tlectures.remove(lecture);
            }
            runningstart = runningstart.plusMinutes(15); //Adds 15 minuts
            runningend = runningend.plusMinutes(15); //Adds 15 minuts
            sbs.add(sb);
        }
        sd.setScheduleblocks(sbs);
        sds.add(sd);
        return sds;
    }

    /**
     * This method returns the schedule time blocks, default is blocks of size 15 minuts from 8:00 to 17:00
     * */
    ScheduleTimes getScheduleTimes(){
        ScheduleTimes sh = new ScheduleTimes();
        for(int i = 0; i < 9 * 4; i++){
            LocalDateTime dt = LocalDate.now().atStartOfDay().plusMinutes(8 * 60 + i * 15);
            System.out.println("getScheduleHours: " + dt.toString());
            sh.getTimes().add(dt);
        }
        return sh;
    }

    List<Lecture> getLectures(LocalDateTime start, LocalDateTime end){
        List<Course> courses = courseservice.getUserCourses();
        List<Lecture> clectures = new ArrayList<>();
        for(Course course: courses){
            List<Lecture> lectures = course.getLectures();
            for(Lecture lecture: lectures){
                if(lecture.getStartdatetime() != null &&
                        lecture.getEnddatetime() != null &&
                        start.compareTo(lecture.getStartdatetime()) < 0 &&
                    end.compareTo(lecture.getEnddatetime()) > -1){
                    clectures.add(lecture);
                }
            }
        }
        System.out.println("Courses found " + courses.size());
        return clectures;
    }
}
