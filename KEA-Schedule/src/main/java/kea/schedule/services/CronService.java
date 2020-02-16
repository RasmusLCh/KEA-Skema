package kea.schedule.services;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class CronService {
    ActionService actionservice;


    public CronService(ActionService actionservice){
        this.actionservice = actionservice;
    }


    @Scheduled(fixedDelay = 30000)
    public void fixedDelayTask() {
        System.out.println("Lets do an action!");
        actionservice.doAction("cron", null);
    }
}
