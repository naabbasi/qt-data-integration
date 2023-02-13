package com.qterminals.entities;

import com.qterminals.constant.ModuleName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(schema = "data_integration_job")
@DynamicUpdate
public class DataIntegrationJob {
    @Id
    @Column(name = "gkey")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genericKey;

    @Column(name = "job_run_date")
    private Date jobRunDate;

    @Column(name = "module_name")
    @Enumerated(EnumType.STRING)
    private ModuleName moduleName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataIntegrationJob)) return false;
        DataIntegrationJob that = (DataIntegrationJob) o;
        return getJobRunDate().equals(that.getJobRunDate()) && getModuleName().equals(that.getModuleName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getJobRunDate(), getModuleName());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DataIntegrationJob.class.getSimpleName() + "[", "]")
                .add("genericKey=" + genericKey)
                .add("jobRunDate=" + jobRunDate)
                .add("moduleName='" + moduleName + "'")
                .toString();
    }
}
