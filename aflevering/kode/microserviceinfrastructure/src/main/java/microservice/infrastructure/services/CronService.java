package microservice.infrastructure.services;

import net.minidev.json.JSONObject;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Handles CRON jobs, multiple cron jobs can be added in this service.
 * */

@Service
@EnableScheduling
public class CronService {
    ActionService actionservice;


    public CronService(ActionService actionservice){
        this.actionservice = actionservice;
    }

    /**
     * Con job that happens every 30 seconds.
     * */
    @Scheduled(fixedDelay = 30000)
    public void fixedDelayTask() {
        System.out.println("Lets do an action!");
        actionservice.doAction("cron", new JSONObject());
    }
}
