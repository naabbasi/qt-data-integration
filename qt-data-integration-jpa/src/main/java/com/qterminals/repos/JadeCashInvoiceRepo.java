package com.qterminals.repos;

import com.qterminals.entities.JadeCashInvoice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JadeCashInvoiceRepo extends CrudRepository<JadeCashInvoice, Long> {
    @Query("select max(genericKey) as genericKey from JadeCashInvoice order by genericKey desc")
    Long getLastSavedRowId();
}
