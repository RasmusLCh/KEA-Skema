package proof.concept.modules.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proof.concept.modules.modules.MicroService;
import proof.concept.modules.modules.MicroServiceAction;
import proof.concept.modules.modules.MicroServicePageInjection;
import proof.concept.modules.repositories.MSSetupActionRepo;
import proof.concept.modules.repositories.MSSetupPageInjectionRepo;
import proof.concept.modules.repositories.MSSetupRepo;

@Service
public class MSSetupService {

    MSSetupRepo mssr;
    MSSetupActionRepo mssar;
    MSSetupPageInjectionRepo msspir;

    @Autowired
    public MSSetupService(MSSetupRepo mssr, MSSetupActionRepo mssar, MSSetupPageInjectionRepo msspir){
        this.mssr = mssr;
        this.mssar = mssar;
        this.msspir = msspir;
    }

    public MicroService serviceregistration(MicroService service){
        return mssr.save(service);
    }

    public void serviceremoval(int msservice_id){
        mssr.deleteById(msservice_id);
    }

    public void serviceaddpageinjection(MicroServicePageInjection pi){
        msspir.save(pi);
    }

    public void serviceaddaction(MicroServiceAction msa){
        mssar.save(msa);
    }
}
