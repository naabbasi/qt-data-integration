package com.qterminals.repos;

import com.qterminals.constant.ModuleName;
import com.qterminals.entities.DataIntegrationJob;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface DataIntegrationJobRepo extends CrudRepository<DataIntegrationJob, Long> {
    @Query("select max(dig.jobRunDate) as jobRunDate from DataIntegrationJob dig where dig.moduleName = :moduleName order by dig.genericKey desc")
    Date getLatestJobRunDate(@Param("moduleName") ModuleName moduleName);
}
