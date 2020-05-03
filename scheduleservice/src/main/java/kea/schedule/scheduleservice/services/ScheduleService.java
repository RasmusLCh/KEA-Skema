package kea.schedule.scheduleservice.services;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import kea.schedule.scheduleservice.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ScheduleService {
    private UserService userservice;
    private CourseService courseservice;
    private ScheduleSettingService schedulesettingservice;
    private LectureService lectureservice;
    private LectureSubjectService lecturesubjectservice;
    private LectureItemService lectureitemservice;


    @Autowired
    public ScheduleService(UserService userservice, CourseService courseservice, ScheduleSettingService schedulesettingservice, LectureService lectureservice, LectureSubjectService lecturesubjectservice, LectureItemService lectureitemservice){
        this.userservice = userservice;
        this.courseservice = courseservice;
        this.schedulesettingservice = schedulesettingservice;
        this.lectureservice = lectureservice;
        this.lecturesubjectservice = lecturesubjectservice;
        this.lectureitemservice = lectureitemservice;
    }

    public List<ScheduleWeekly> getSchedulesWeekly(LocalDate start, LocalDate end){
        return getSchedulesWeekly(start.atStartOfDay(), end.atStartOfDay().plusHours(23).plusMinutes(59));
    }

    public List<ScheduleWeekly> getSchedulesWeekly(LocalDateTime start, LocalDateTime end){
        List<Lecture> lectures = getLectures(start, end);
        ScheduleTimes scheduletimes = getScheduleTimes();
        List<ScheduleWeekly> schedulesweekly = new ArrayList<>();
        LocalDate runningdate = start.toLocalDate();
        WeekFields weekFields = WeekFields.ISO;
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

            if(dayofweek == 1 || (first && dayofweek < 6)){
                sw = new ScheduleWeekly();
                sw.setWeeknumber(weeknum);
                sw.setScheduletimes(new ScheduleTimes(scheduletimes));
                if(first){
                    //If we dont start on a monday, we add empty ScheduleDaily
                    //Starting by generating dummy blocks
                    List<ScheduleBlock> sbs = new ArrayList<>();
                    for(int i = 0; i < scheduletimes.getTimes().size() * 4; i++){
                        ScheduleBlock sb = new ScheduleBlock();
                        sb.setSize(1);
                        //sb.setMessage("");
                        sbs.add(sb);
                    }

                    for(int i = 1; i < dayofweek; i++){
                        ScheduleDaily tsd = new ScheduleDaily();
                        tsd.setScheduletimes(new ScheduleTimes(scheduletimes));
                        tsd.setScheduleblocks(sbs);
                        sw.getScheduledailies().add(tsd);
                    }
                }
                first = false;
            }
            if(dayofweek < 6 && !first){
                sds = getSchedulesDaily(runningdate.atStartOfDay(), runningdate.atTime(23, 59), new ScheduleTimes(scheduletimes), lectures);
                if(sds != null && sds.size() == 1){
                    sd = sds.get(0);

                    sw.getScheduledailies().add(sd);
                }
                else{
                    sd = null;
                }
            }
            if(dayofweek == 7 && !first){
                //save
                preTrim(sw);
                postTrim(sw);
                schedulesweekly.add(sw);
                sw = null;
            }
            runningdate = runningdate.plusDays(1);
        }
        if(sw != null){
            preTrim(sw);
            postTrim(sw);
            schedulesweekly.add(sw);
        }
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
        for(int i = 0; i < schedulehours.getTimes().size(); i++){
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
                if(runningstart.compareTo(lecture.getStartdatetime()) >= 0 && runningend.compareTo(lecture.getEnddatetime()) < 0){
                    //Found a lecture
                    long secondsdif = lecture.getEnddatetime().toEpochSecond(ZoneOffset.ofHours(1)) - lecture.getStartdatetime().toEpochSecond(ZoneOffset.ofHours(1));
                    int size = (int)Math.ceil((secondsdif / 60) / 15);
                    sb.getLectures().add(lecture);
                    sb.setSize(size);
                    skipnum = size - 1;
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
     * Removes empty blocks before lectures
     * */
    public ScheduleWeekly preTrim(ScheduleWeekly sw){
        int i = 0;
        boolean remove = true;
        List<Integer> removes = new ArrayList<>();
        for(LocalDateTime dt : sw.getScheduletimes().getTimes()){
            for(ScheduleDaily sd : sw.getScheduledailies()){
                if(!(sd.getScheduleblocks().get(i).getSize() == 1 &&
                    sd.getScheduleblocks().get(i).getLectures().size() == 0 &&
                    sd.getScheduleblocks().get(i).getMessage() == null)){
                    remove = false;
                }
            }
            if(remove){
                removes.add(i);
                i++;
            }
            else{
                //We are done
                break;
            }
        }
        //Remove rows that doesnt contain data

        for(int j = 0; j < i; j++){
            sw.getScheduletimes().getTimes().remove(0);
            for(int k = 0; k < sw.getScheduledailies().size(); k++){
                sw.getScheduledailies().get(k).getScheduletimes().getTimes().remove(0);
                sw.getScheduledailies().get(k).getScheduleblocks().remove(0);
            }
        }
        return sw;
    }

    /**
     * Removes empty blocks after lectures
     * */
    public ScheduleWeekly postTrim(ScheduleWeekly sw){
    //sw.getScheduledailies().get(0).getScheduleblocks().get(0).getLectures() == null
    int i =  1;
    boolean remove = true;
    List<Integer> removes = new ArrayList<>();
    for(LocalDateTime dt : sw.getScheduletimes().getTimes()){
        for(ScheduleDaily sd : sw.getScheduledailies()){
            if(!(sd.getScheduleblocks().get(sd.getScheduleblocks().size() - i).getSize() == 1 &&
                    sd.getScheduleblocks().get(sd.getScheduleblocks().size() - i).getLectures().size() == 0 &&
                    sd.getScheduleblocks().get(sd.getScheduleblocks().size() - i).getMessage() == null)){
                remove = false;
            }
        }
        if(remove){
            removes.add(i);
            i++;
        }
        else{
            //We are done
            break;
        }
    }
    //Remove rows that doesnt contain data
    for(int j = 1; j < i; j++){
        sw.getScheduletimes().getTimes().remove(sw.getScheduletimes().getTimes().size() - 1);
        for(int k = 0; k < sw.getScheduledailies().size(); k++){
            sw.getScheduledailies().get(k).getScheduletimes().getTimes().remove(sw.getScheduledailies().get(k).getScheduletimes().getTimes().size() - 1);
            sw.getScheduledailies().get(k).getScheduleblocks().remove(sw.getScheduledailies().get(k).getScheduleblocks().size() - 1);
        }
    }
    return sw;
}

    /**
     * This method returns the schedule time blocks, default is blocks of size 15 minuts from 8:00 to 17:00
     * */
    ScheduleTimes getScheduleTimes(){
        ScheduleTimes sh = new ScheduleTimes();
        for(int i = 0; i < 9 * 4; i++){
            LocalDateTime dt = LocalDate.now().atStartOfDay().plusMinutes(8 * 60 + i * 15);
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
        return clectures;
    }

    public int getCurrentWeek(){
        LocalDateTime now = LocalDateTime.now();
        WeekFields weekFields = WeekFields.ISO;
        return now.get(weekFields.weekOfWeekBasedYear());
    }

    public int getCurrentWeekDay(){
        LocalDateTime now = LocalDateTime.now();
        WeekFields weekFields = WeekFields.ISO;
        return now.get(weekFields.dayOfWeek());
    }

    public boolean setPeriod(String start, String end){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        boolean result = false;
        try {
            LocalDate startdate = LocalDate.parse(start, formatter);
            LocalDate enddate = LocalDate.parse(end, formatter);
            //If enddate is equal to or after startdate lets save
            if(enddate.compareTo(startdate) > -1){
                setPeriodStart(start);
                setPeriodEnd(end);
                result = true;

                updateCourseActiveState(startdate, enddate);
            }
        }
        catch (Exception e){
            System.out.println("setPeriod: Exception = " + e.getMessage());
        }
        return result;
    }

    /**
    * Enable / disable courses based on periode startdate and periode enddate set in the system.
    * */
    public void updateCourseActiveState(){
        updateCourseActiveState(getPeriodStart(), getPeriodEnd());
    }
    /**
     * Enable / disable courses based on startdate and enddate and the date the courses has lectures.
     * */
    @Transactional
    public void updateCourseActiveState(LocalDate startdate, LocalDate enddate){
        //It's time to deactivate / activate courses
        LocalDate lecturestartdate;
        LocalDate lectureenddate;
        for(Course cs: courseservice.findAll()){
            //We need to check the lectures, lectures are sorted so lowest are first
            if(cs.getLectures() != null && cs.getLectures().size() > 0) {
                //if the lecture with highest date is before periodstart, then deactivate course
                lecturestartdate = cs.getLectures().get(0).getStartdatetime().toLocalDate();
                lectureenddate = cs.getLectures().get(cs.getLectures().size() - 1).getStartdatetime().toLocalDate();
                if (lectureenddate.compareTo(startdate) < 0){
                    cs.setActive(false);
                }
                //If the lecture with lowest date is after periodend, then deactivate course
                else if(lecturestartdate.compareTo(enddate) > 0){
                    cs.setActive(false);
                }
                //Else set course as activated
                else{
                    cs.setActive(true);
                }
            }
            //Deactivate, not lectures in course
            else{
                cs.setActive(false);
            }
            courseservice.edit(cs);
        }
    }

    /**
     * Returns true on success and false on error, the string must be in the format yyyy-MM-dd
     * */
    private void setPeriodStart(String start){
        schedulesettingservice.setSetting("periodstart", start);
    }

    /**
     * Returns true on success and false on error, the string must be in the format yyyy-MM-dd
     * */
    private void setPeriodEnd(String end){
        schedulesettingservice.setSetting("periodend", end);
    }

    /**
     * Returns the periodstart, that is used to calculate which courses are shown in schedule. If no periodstart has been set, now() is returned.
     * */
    public LocalDate getPeriodStart(){
        String periodstart = schedulesettingservice.findSetting("periodstart");
        LocalDate ld = LocalDate.now();
        if(periodstart != null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try
            {
                ld = LocalDate.parse(periodstart, formatter);
            }
            catch (Exception e){
            }
        }
        return ld;
    }

    /**
     * Returns the periodstart repræsented as a string
     * */
    public String getPeriodStartStr(){
        return schedulesettingservice.findSetting("periodstart");
    }

    /**
     * Returns the periodstart, that is used to calculate which courses are shown in schedule. If no periodstart has been set, now() is returned.
     * */
    public LocalDate getPeriodEnd(){
        String periodend = schedulesettingservice.findSetting("periodend");
        LocalDate ld = LocalDate.now();
        if(periodend != null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try
            {
                ld = LocalDate.parse(periodend, formatter);
            }
            catch (Exception e){
            }
        }
        return ld;
    }

    /**
     * Returns the periodend repræsented as a string
     * */
    public String getPeriodEndStr(){
        return schedulesettingservice.findSetting("periodend");
    }

    public void importCSV(MultipartFile courseplancsv) throws IOException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(String[].class).withColumnSeparator(';');
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        ObjectReader reader = mapper.readerFor(String[].class).with(schema);
        MappingIterator<String[]> it = reader.readValues(courseplancsv.getBytes());
        int linenum = 0;
        String[] header = null;
        List<Lecture> lectures = new ArrayList();
        Lecture lecture = null;
        //This variable indicates if we recently created a lecture
        boolean newlycreated = false;
        String curdate = null;
        LectureSubject[] lecturesubjects = null;
        List<LectureItem> lectureitems;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        //Remove all lectures from the course
        for(Lecture slecture: courseservice.getSelectedCourse().getLectures()){
            lectureservice.delete(slecture.getId());
        }
        while (it.hasNext()) {

            String[] row = it.next();
            if(linenum == 0){
                header = row;
            }
            else {
                int cellnum = 0;
                for (String cell : row) {
                    //INITIAL STARTUP
                    if (cellnum == 0 && !cell.equals("")) {
                        //New lecture
                        //Save lecture subject to lecture and save lecture
                        if (lecture != null) {
                            //Save the lecture
                            List<LectureSubject> lecturesubjectlist = new ArrayList();
                            for (LectureSubject ls : lecturesubjects) {
                                lecturesubjectlist.add(ls);
                            }
                            lecture.setLecturesubjects(lecturesubjectlist);
                            lecture.setCourse(courseservice.getSelectedCourse());
                            lectureservice.create(lecture);
                            //Save the lecturesubjects
                            for(LectureSubject ls : lecture.getLecturesubjects()){
                                ls.setLecture(lecture);
                                lecturesubjectservice.create(ls);
                                for(LectureItem li : ls.getLectureitems()){
                                    li.setLecturesubject(ls);
                                    lectureitemservice.create(li);
                                }
                            }
                        }
                        //Prepare for new data
                        lecture = new Lecture();
                        lecturesubjects = new LectureSubject[header.length - 5];
                        for(int i = 0; i < header.length-5; i++)
                        {
                            lecturesubjects[i] = new LectureSubject(header[i+5], 50 - i);
                        }
                        curdate = cell;
                        newlycreated = true;
                    }
                    //Done for each row
                    if (newlycreated && cellnum == 1 && !cell.equals("")) {
                        lecture.setStartdatetime(LocalDateTime.parse(curdate + " " + cell, formatter));
                        System.out.println("startdatetime: " + lecture.getStartdatetime().toString());
                    }
                    if (newlycreated && cellnum == 2 && !cell.equals("")) {
                        lecture.setEnddatetime(LocalDateTime.parse(curdate + " " + cell, formatter));
                        System.out.println("enddatetime: " + lecture.getEnddatetime().toString());
                    }
                    if (cellnum == 3 && !cell.equals("")) {
                        //Add teacher to lecture
                        User usr = userservice.findByDisplayname(cell);
                        if (usr != null) {
                            lecture.getTeachers().add(usr);
                        } else {
                            System.out.println("Error finding displayname for " + cell);
                        }
                    }
                    if (cellnum == 4 && !cell.equals("")) {
                        lecture.setLocation(cell);
                    }
                    if (cellnum > 4 && !cell.equals("")) {
                        //add items(s) to subject
                        lecturesubjects[cellnum - 5].addLectureitem(new LectureItem(cell, 50 - cellnum - 5));
                    }
                    cellnum++;
                }
                newlycreated = false;
            }
            linenum++;
        }
        if (lecture != null) {
            List<LectureSubject> lecturesubjectlist = new ArrayList();
            for (LectureSubject ls : lecturesubjects) {
                lecturesubjectlist.add(ls);
            }
            lecture.setLecturesubjects(lecturesubjectlist);
            lecture.setCourse(courseservice.getSelectedCourse());
            lectureservice.create(lecture);
            for(LectureSubject ls : lecture.getLecturesubjects()){
                ls.setLecture(lecture);
                lecturesubjectservice.create(ls);
                for(LectureItem li : ls.getLectureitems()){
                    li.setLecturesubject(ls);
                    lectureitemservice.create(li);
                }
            }
        }
        //Set the course to active
        courseservice.getSelectedCourse().setActive(true);
        courseservice.edit(courseservice.getSelectedCourse());
    }
}
