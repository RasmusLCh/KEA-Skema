package kea.schedule.scheduleservice.controllers.admin;

import kea.schedule.scheduleservice.models.ScheduleSetting;
import kea.schedule.scheduleservice.services.CourseService;
import kea.schedule.scheduleservice.services.ScheduleService;
import kea.schedule.scheduleservice.services.ScheduleSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/servicepages/KEA-Schedule-Admin/")
public class AdminController {
    private CourseService courseservice;
    private ScheduleService scheduleservice;

    @Value("${ms.port.service:7510}")
    int serviceport;

    @Autowired
    public AdminController(CourseService courseservice, ScheduleService scheduleservice){
        this.courseservice = courseservice;
        this.scheduleservice = scheduleservice;
    }

    @GetMapping({"", "index", "index.eng", "index.dk"})
    public String get_root(HttpServletRequest hsr, Model model){
        model.addAttribute("periodstart", scheduleservice.getPeriodStartStr());
        model.addAttribute("periodend", scheduleservice.getPeriodEndStr());
        if(hsr.getLocalPort() == serviceport){
            return "admin/index";
        }
        return "forbidden";
    }

    @PostMapping({"", "index", "index.eng", "index.dk"})
    public String post_root(@RequestParam(name = "periodstart", required=true) String start,  @RequestParam(name = "periodend", required=true) String end, HttpServletRequest hsr, Model model){

        System.out.println("PostMappingPostMappingPostMappingPostMapping");
        if(!scheduleservice.setPeriod(start, end)){
            model.addAttribute("period_error", true);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        model.addAttribute("periodstart", start);
        model.addAttribute("periodend", end);
        if(hsr.getLocalPort() == serviceport){
            return "admin/index";
        }
        return "forbidden";
    }

    @GetMapping({"qa.eng", "qa"})
    public String get_qa_eng(HttpServletRequest hsr){
        if(hsr.getLocalPort() == serviceport){
            return "admin/qa";
        }
        return "forbidden";
    }

    @GetMapping({"qa.dk"})
    public String get_qa_dk(HttpServletRequest hsr){
        if(hsr.getLocalPort() == serviceport){
            return "admin/qa";
        }
        return "forbidden";
    }
}