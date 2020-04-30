package kea.schedule.scheduleservice.controllers.teacher;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import kea.schedule.scheduleservice.models.*;
import kea.schedule.scheduleservice.services.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/servicepages/KEA-Schedule-Teacher/")
public class TeacherController {
    @Value("${ms.port.teacher:7512}")
    int teacherport;

    private CourseService courseservice;
    private UserService userservice;
    private LectureService lectureservice;
    private LectureSubjectService lecturesubjectservice;
    private LectureItemService lectureitemservice;

    public TeacherController(CourseService courseservice, LectureService lectureservice, LectureSubjectService lecturesubjectservice, LectureItemService lectureitemservice, UserService userservice){
        this.courseservice = courseservice;
        this.userservice = userservice;
        this.lectureservice = lectureservice;
        this.lecturesubjectservice = lecturesubjectservice;
        this.lectureitemservice = lectureitemservice;
    }

    @GetMapping({"", "/", "index", "index.eng"})
    public String get_root(HttpServletRequest hsr, Model model){
        System.out.println("get_root teacher");
        if(hsr.getLocalPort() == teacherport){
            System.out.println(model.getAttribute("selectedcourseid"));
            return "teacher/index";
        }
        return "forbidden";
    }

    @PostMapping({"", "/", "index", "index.eng"})
    public String post_root(@RequestParam(name="selectedcourseid", required=true) int selectedcourseid, HttpServletRequest hsr, Model model){
        if(hsr.getLocalPort() == teacherport){
            courseservice.setSelectedcourse(selectedcourseid, model);

            System.out.println(model.getAttribute("selectedcourseid"));
            return "redirect:index";
        }
        return "forbidden";
    }

    @PostMapping("uploadcoursecsv")
    public String post_uploadcoursecsv(@RequestParam("courseplancsv") MultipartFile courseplancsv, HttpServletRequest hsr) throws IOException {
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
                System.out.println("array");
                int cellnum = 0;
                for (String cell : row) {
                    System.out.println("a" + cell);
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
        /*
        Iterator<Map<String, String>> iterator = mapper
                .readerFor(Map.class)
                .with(CsvSchema.emptySchema().withHeader())
                .readValues(courseplancsv.getBytes());
        while (iterator.hasNext()) {
            Map<String, String> keyVals = iterator.next();
            MappingIterator<String[]> it = new CsvMapper().readerFor(String[].class).readValues(keyVals.toString());
            while(it.hasNext()){
                Map<String, String> val = iterator.next();
                System.out.println("m" + val.toString());
            }
            System.out.println(keyVals);
        }

         */
        /*
        if(hsr.getLocalPort() == teacherport){
            try {
                    CsvSchema bootstrap = CsvSchema.emptySchema().withHeader();
                    CsvMapper csvMapper = new CsvMapper();
                    MappingIterator<Map<String, String>> mappingIterator = csvMapper.readerWithSchemaFor(Map.class).with(bootstrap).readValues(courseplancsv.getBytes());

                    while(mappingIterator.hasNext()){
                        Map<String, String> row = mappingIterator.next();
                        int i = 0;
                        for(Map.Entry<String, String> e : row.entrySet()){

                            System.out.println(i + ": " + e.getKey() + e.getValue());
                            i++;
                        }

                    }


        } catch (IOException e) {
                e.printStackTrace();
            }

        }
            return "redirect:index";

         */
        return "redirect:index";
    }

    @ModelAttribute("selectedcourseid")
    public int selected_courseid(){
        return courseservice.getSelectedCourseId();
    }

    @ModelAttribute("selectedcourse")
    public Course selected_course(){
        return courseservice.getSelectedCourse();
    }

    @ModelAttribute("courses")
    public List<Course> modelattribute_courses(){
        //Should only return the courses the teacher has access too.
        return courseservice.findAllByAccess();
    }
}
