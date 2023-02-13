package com.qterminals.entities;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(schema = "jade_cash_invoice")
@DynamicUpdate
public class JadeCashInvoice implements Serializable {

    @Id
    @Column(name = "gkey")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genericKey;

    @CsvDate(value = "dd-MMM-yyyy")
    @CsvBindByPosition(position = 0)
    private LocalDate invoiceDate;

    @CsvBindByPosition(position = 1)
    private String receiptNo;

    @CsvBindByPosition(position = 2)
    private BigDecimal amount;

    @CsvBindByPosition(position = 3)
    private BigDecimal column1;

    @CsvBindByPosition(position = 4)
    private BigDecimal column2;

    @CsvBindByPosition(position = 5)
    private BigDecimal column3;

    @CsvBindByPosition(position = 6)
    private BigDecimal column4;

    @CsvBindByPosition(position = 7)
    private BigDecimal column5;

    @CsvBindByPosition(position = 8)
    private BigDecimal column6;

    @CsvBindByPosition(position = 9)
    private BigDecimal column7;

    @CsvBindByPosition(position = 10)
    private BigDecimal column8;

    @CsvBindByPosition(position = 11)
    private BigDecimal column9;

    @CsvBindByPosition(position = 12)
    private BigDecimal column10;

    @CsvBindByPosition(position = 13)
    private BigDecimal column11;

    @CsvBindByPosition(position = 14)
    private BigDecimal column12;

    @CsvBindByPosition(position = 15)
    private BigDecimal column13;

    @CsvBindByPosition(position = 16)
    private BigDecimal column14;

    @CsvBindByPosition(position = 17)
    private BigDecimal column15;

    @CsvBindByPosition(position = 18)
    private BigDecimal column16;

    @CsvBindByPosition(position = 19)
    private BigDecimal column17;

    @CsvBindByPosition(position = 20)
    private BigDecimal column18;

    @CsvBindByPosition(position = 21)
    private BigDecimal column19;

    @CsvBindByPosition(position = 22)
    private BigDecimal column20;

    @CsvBindByPosition(position = 23)
    private BigDecimal column21;

    @CsvBindByPosition(position = 24)
    private BigDecimal column22;

    @CsvBindByPosition(position = 25)
    private BigDecimal column23;

    @CsvBindByPosition(position = 26)
    private BigDecimal column24;

    @CsvBindByPosition(position = 27)
    private BigDecimal column25;

    @CsvBindByPosition(position = 28)
    private BigDecimal column26;

    @CsvBindByPosition(position = 29)
    private BigDecimal column27;

    @CsvBindByPosition(position = 30)
    private BigDecimal column28;

    @CsvBindByPosition(position = 31)
    private BigDecimal column29;
}
