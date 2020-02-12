package proof.concept.modules.services;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import proof.concept.modules.repositories.ActionRepo;

import javax.swing.*;

@Service
@EnableScheduling
public class CronService {
    ActionService actionservice;


    public CronService(ActionService actionservice){
        this.actionservice = actionservice;
    }


    @Scheduled(fixedDelay = 2000)
    public void fixedDelayTask() {
        System.out.println("Lets do an action!");
        actionservice.doAction("cron", null);
    }
}
