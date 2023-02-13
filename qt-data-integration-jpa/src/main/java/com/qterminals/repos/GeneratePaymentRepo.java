package com.qterminals.repos;

import com.qterminals.entities.GeneratePayment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface GeneratePaymentRepo extends CrudRepository<GeneratePayment, Long> {
    @Query("select max(lastRunDate) as lastRunDate from GeneratePayment order by genericKey desc")
    Date getLastRunDate();
}
