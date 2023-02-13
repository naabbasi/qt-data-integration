package com.qterminals.services;

import com.qterminals.entities.GeneratePayment;
import com.qterminals.repos.GeneratePaymentRepo;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GeneratePaymentService {
    private GeneratePaymentRepo generatePaymentRepo;

    public GeneratePaymentService(GeneratePaymentRepo generatePaymentRepo) {
        this.generatePaymentRepo = generatePaymentRepo;
    }

    public GeneratePayment save(GeneratePayment generatePayment){
        return this.generatePaymentRepo.save(generatePayment);
    }

    public Iterable<GeneratePayment> saveAll(Iterable<GeneratePayment>  generatePayment){
        return this.generatePaymentRepo.saveAll(generatePayment);
    }

    public Iterable<GeneratePayment> list(){
        return this.generatePaymentRepo.findAll();
    }

    public Date getLastRunDate(){
        return this.generatePaymentRepo.getLastRunDate();
    }
}
