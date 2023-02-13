package com.qterminals.repos;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class JadeCashInvoiceHelper {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional //jade_cash_invoice
    public Boolean emptyTable(){
        Query query = this.entityManager.createNativeQuery("truncate table jade_cash_invoice");
        return query.executeUpdate() > 1 ? true : false;
    }
}
