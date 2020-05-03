package kea.schedule.scheduleservice.services;

import kea.schedule.scheduleservice.models.ScheduleSetting;
import kea.schedule.scheduleservice.repositories.ScheduleSettingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleSettingService implements CRUDServiceInterface<ScheduleSetting> {
    private ScheduleSettingRepo repo;
    private ActionService actionservice;

    @Autowired
    public ScheduleSettingService(ScheduleSettingRepo repo, ActionService actionservice){
        this.repo = repo;
        this.actionservice = actionservice;
    }

    @Override
    public ScheduleSetting create(ScheduleSetting scheduleSetting) {
        ScheduleSetting l = repo.save(scheduleSetting);
        actionservice.doAction("ScheduleSettingService.create", l);
        return l;
    }

    @Override
    public void edit(ScheduleSetting scheduleSetting) {
        repo.save(scheduleSetting);
        actionservice.doAction("ScheduleSettingService.edit", scheduleSetting);
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
        actionservice.doAction("ScheduleSettingService.delete", new ScheduleSetting(id));
    }

    @Override
    public ScheduleSetting findById(int id) {
        Optional opt = repo.findById(id);
        if(opt.isPresent()){
            return (ScheduleSetting)opt.get();
        }
        return null;
    }

    @Override
    public List<ScheduleSetting> findAll() {
        return repo.findAll();
    }

    public String findSetting(String name){
        ScheduleSetting s = repo.findByName(name);
        if(s == null){
            return null;
        }
        else{
            return s.getValue();
        }
    }

    public void setSetting(String name, String value){
        ScheduleSetting s = repo.findByName(name);
        if(s == null){
            create(new ScheduleSetting(name, value));
        }
        else{
            s.setValue(value);
            edit(s);
        }
    }
}
