package com.qterminals.services;

import com.qterminals.entities.JadeCashInvoice;
import com.qterminals.repos.JadeCashInvoiceHelper;
import com.qterminals.repos.JadeCashInvoiceRepo;
import org.springframework.stereotype.Service;

@Service
public class JadeCashInvoiceService {
    private JadeCashInvoiceRepo jadeCashInvoiceRepo;
    private JadeCashInvoiceHelper jadeCashInvoiceHelper;

    public JadeCashInvoiceService(JadeCashInvoiceRepo jadeCashInvoiceRepo, JadeCashInvoiceHelper jadeCashInvoiceHelper) {
        this.jadeCashInvoiceRepo = jadeCashInvoiceRepo;
        this.jadeCashInvoiceHelper = jadeCashInvoiceHelper;
    }

    public JadeCashInvoice save(JadeCashInvoice jadeCashInvoice){
        return this.jadeCashInvoiceRepo.save(jadeCashInvoice);
    }

    public Iterable<JadeCashInvoice> saveAll(Iterable<JadeCashInvoice>  jadeCashInvoice){
        return this.jadeCashInvoiceRepo.saveAll(jadeCashInvoice);
    }

    public Iterable<JadeCashInvoice> list(){
        return this.jadeCashInvoiceRepo.findAll();
    }

    public Long lastSavedRowId(){
        return this.jadeCashInvoiceRepo.getLastSavedRowId();
    }

    public Boolean emptyTable(){
        return this.jadeCashInvoiceHelper.emptyTable();
    }
}
