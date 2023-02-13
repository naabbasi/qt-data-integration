package com.qterminals;

import com.qterminals.excel.ExcelUtil;
import com.qterminals.generate.JadeCashInvoiceGenerator;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Path;

@SpringBootApplication
public class QTDataIntegrationJDBCApplication {
    public static void main(String[] args) {
        SpringApplication.run(QTDataIntegrationJDBCApplication.class, args);

        ExcelUtil excelUtil = new ExcelUtil();
        Sheet sheet = excelUtil.readFile("C:\\temp\\01-04-Jun-2022 JAD Report.xlsx");
        Path path = Path.of("C:\\temp\\test.csv");
        excelUtil.convertExcelToCSV(sheet, path);

        JadeCashInvoiceGenerator jadeCashInvoiceGenerator = new JadeCashInvoiceGenerator();
        jadeCashInvoiceGenerator.generate(Path.of("C:\\temp"), "/jade_queries/Jade_Cash_Invoice.sql", "/jade_queries/Jade_Distribution.sql", "/jade_queries/Jade_Receipts.sql");
    }
}
