package com.qterminals.entities;

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
@Table(schema = "generate_payment")
@DynamicUpdate
public class GeneratePayment {
    @Id
    @Column(name = "gkey")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genericKey;

    @Column(name = "last_run_date")
    private Date lastRunDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeneratePayment)) return false;
        GeneratePayment that = (GeneratePayment) o;
        return getGenericKey().equals(that.getGenericKey()) && getLastRunDate().equals(that.getLastRunDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGenericKey(), getLastRunDate());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GeneratePayment.class.getSimpleName() + "[", "]")
                .add("genericKey=" + genericKey)
                .add("lastRunDate=" + lastRunDate)
                .toString();
    }
}
