package kea.schedule.scheduleservice.controllers.schedule;

import kea.schedule.scheduleservice.components.MSSession;
import kea.schedule.scheduleservice.models.ScheduleWeekly;
import kea.schedule.scheduleservice.services.ScheduleService;
import kea.schedule.scheduleservice.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Schedule microservice default controller. This controller shows the schedule for teachers and students.
 * */

@Controller
@RequestMapping("/servicepages/KEA-Schedule/")
public class ScheduleController {
    @Value("${ms.port.student:7511}")
    int studentport;
    UserService userservice;
    MSSession session;
    ScheduleService scheduleservice;
    public ScheduleController(MSSession session, UserService userservice, ScheduleService scheduleservice){
        this.session = session;
        this.userservice = userservice;
        this.scheduleservice = scheduleservice;
    }

    @GetMapping({"", "/", "index", "index.eng"})
    public String get_root(HttpServletRequest hsr, Model model){
        if(hsr.getLocalPort() == studentport){

            List<ScheduleWeekly> schedulesweekly = scheduleservice.getSchedulesWeekly(scheduleservice.getPeriodStart(), scheduleservice.getPeriodEnd());
            model.addAttribute("schedulesweekly", schedulesweekly);
            return "/schedule/weekly_eng";
        }
        return "redirect:/weekly_eng";
    }

    @GetMapping({"weekly.eng"})
    public String get_weekly(HttpServletRequest hsr, Model model){
        if(hsr.getLocalPort() == studentport) {
            List<ScheduleWeekly> schedulesweekly = scheduleservice.getSchedulesWeekly(scheduleservice.getPeriodStart(), scheduleservice.getPeriodEnd());
            model.addAttribute("schedulesweekly", schedulesweekly);
            return "/schedule/weekly_eng";
        }
        return "forbidden";
    }

    @GetMapping({"index.dk"})
    public String get_root_dk(HttpServletRequest hsr, Model model){
        if(hsr.getLocalPort() == studentport){

            List<ScheduleWeekly> schedulesweekly = scheduleservice.getSchedulesWeekly(scheduleservice.getPeriodStart(), scheduleservice.getPeriodEnd());
            model.addAttribute("schedulesweekly", schedulesweekly);
            return "/schedule/weekly_dk";
        }
        return "redirect:/weekly_dk";
    }

    @GetMapping({"weekly.dk"})
    public String get_weekly_dk(HttpServletRequest hsr, Model model){
        if(hsr.getLocalPort() == studentport) {
            List<ScheduleWeekly> schedulesweekly = scheduleservice.getSchedulesWeekly(scheduleservice.getPeriodStart(), scheduleservice.getPeriodEnd());
            model.addAttribute("schedulesweekly", schedulesweekly);
            return "/schedule/weekly_dk";
        }
        return "forbidden";
    }

    @ModelAttribute("currentWeek")
    public int getCurrentWeek(){
        return scheduleservice.getCurrentWeek();
    }

    @ModelAttribute("currentWeekDay")
    public int getCurrentWeekDay(){
        return scheduleservice.getCurrentWeekDay();
    }

    @ModelAttribute("userid")
    public int getUserId(){
        return session.getUserId();
    }
}
