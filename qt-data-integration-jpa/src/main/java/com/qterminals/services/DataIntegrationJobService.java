package com.qterminals.services;

import com.qterminals.constant.ModuleName;
import com.qterminals.entities.DataIntegrationJob;
import com.qterminals.repos.DataIntegrationJobRepo;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DataIntegrationJobService {
    private DataIntegrationJobRepo dataIntegrationJobRepo;

    public DataIntegrationJobService(DataIntegrationJobRepo dataIntegrationJobRepo) {
        this.dataIntegrationJobRepo = dataIntegrationJobRepo;
    }

    public DataIntegrationJob save(DataIntegrationJob dataIntegrationJob){
        return this.dataIntegrationJobRepo.save(dataIntegrationJob);
    }

    public Iterable<DataIntegrationJob> saveAll(Iterable<DataIntegrationJob>  dataIntegrationJob){
        return this.dataIntegrationJobRepo.saveAll(dataIntegrationJob);
    }

    public Iterable<DataIntegrationJob> list(){
        return this.dataIntegrationJobRepo.findAll();
    }

    public Date getLatestJobRunDate(ModuleName moduleName){
        return this.dataIntegrationJobRepo.getLatestJobRunDate(moduleName);
    }
}
