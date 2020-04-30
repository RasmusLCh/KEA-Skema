package kea.schedule.scheduleservice.controllers.schedule;

import kea.schedule.scheduleservice.components.MSSession;
import kea.schedule.scheduleservice.models.ScheduleBlock;
import kea.schedule.scheduleservice.models.ScheduleDaily;
import kea.schedule.scheduleservice.models.ScheduleWeekly;
import kea.schedule.scheduleservice.services.ScheduleService;
import kea.schedule.scheduleservice.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

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

            List<ScheduleWeekly> schedulesweekly = scheduleservice.getSchedulesWeekly(LocalDateTime.now(), LocalDateTime.now().plusWeeks(10));
            for(ScheduleWeekly sw : schedulesweekly){
                System.out.println(sw.getScheduledailies().size());
                for(ScheduleDaily sd : sw.getScheduledailies()){
                    System.out.println(sd.getScheduleblocks().size());
                }
            }
            model.addAttribute("schedulesweekly", schedulesweekly);
            return "schedule/weekly";
        }
        return "forbidden";
    }

    @GetMapping({"weekly.eng"})
    public String get_weekly(HttpServletRequest hsr){
        if(hsr.getLocalPort() == studentport) {
            return "schedule/weekly";
        }
        return "forbidden";
    }
}
