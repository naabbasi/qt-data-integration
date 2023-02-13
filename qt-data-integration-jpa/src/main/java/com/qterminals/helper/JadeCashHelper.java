package com.qterminals.helper;

import com.qterminals.csv.CSVUtils;
import com.qterminals.dto.CsvTransfer;
import com.qterminals.entities.JadeCashInvoice;
import com.qterminals.excel.ExcelUtil;
import com.qterminals.ftp.FTPUtils;
import com.qterminals.generate.JadeCashInvoiceGenerator;
import com.qterminals.services.JadeCashInvoiceService;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public final class JadeCashHelper {
    private JadeCashInvoiceService jadeCashInvoiceService;
    private FTPUtils ftpUtils;

    public JadeCashHelper(JadeCashInvoiceService jadeCashInvoiceService, FTPUtils ftpUtils) {
        this.jadeCashInvoiceService = jadeCashInvoiceService;
        this.ftpUtils = ftpUtils;
    }

    public Map<String, Object> parseExcelFile(Path directoryPath, List<Path> excelFilesPath, String fileName){
        final Map<String, Object> responseMap = new HashMap<>();
        final ExcelUtil excelUtil = new ExcelUtil();
        final Path csvFile = directoryPath.resolve(Path.of(fileName.replace(".xlsx", ".csv")));

        List<String> convertedExcelsAsCSV = new ArrayList<>();
        for(Path path : excelFilesPath){
            Sheet sheet = excelUtil.readFile(path.toAbsolutePath().toString());
            convertedExcelsAsCSV.addAll(excelUtil.getRawCSV(sheet));
        }

        if(!convertedExcelsAsCSV.isEmpty()){
            excelUtil.writeCSV(convertedExcelsAsCSV, csvFile);
            responseMap.put("csvFile", (csvFile.getFileName().toString() + " has been generated successfully"));
        }

        CSVUtils<JadeCashInvoice> csvUtils = new CSVUtils();

        Long lastSavedRowId = this.jadeCashInvoiceService.lastSavedRowId();
        if(lastSavedRowId != null){
            this.jadeCashInvoiceService.emptyTable();
            responseMap.put("emptyTable", "Table is ready");
        } else {
            responseMap.put("emptyTable", "Table is ready");
        }

        CsvTransfer<JadeCashInvoice> csvTransfer = csvUtils.getJadeCashInvoiceCsvTransfer(csvFile, JadeCashInvoice.class);
        if(csvTransfer != null){
            List<JadeCashInvoice> jadeCashInvoiceCsvBeanList = csvTransfer.getCsvList();
            this.jadeCashInvoiceService.saveAll(jadeCashInvoiceCsvBeanList);
            responseMap.put("storeTable", "All row(s) have been inserted into the Table");

            JadeCashInvoiceGenerator jadeCashInvoiceGenerator = new JadeCashInvoiceGenerator();
            jadeCashInvoiceGenerator.generate(directoryPath, "/jade_queries/Jade_Cash_Invoice.sql", "/jade_queries/Jade_Distribution.sql", "/jade_queries/Jade_Receipts.sql");

            responseMap.put("integrationFiles", "Jade cash reports have been generated");

            List<Path> dataCSVFiles = jadeCashInvoiceGenerator.getGetGeneratedFiles();

            for(Path path : dataCSVFiles){
                String uploadDirectory = "QTIntegration/Jade/CashInvoices/Extracts/FBDI";
                if(path.getFileName().toString().equals("QTJadeReceiptExtracts.csv")){
                    uploadDirectory = "QTIntegration/Jade/Receipts/Extracts";
                }

                this.ftpUtils.uploadFile(path, uploadDirectory);
            }

            responseMap.put("integrationFilesUploaded", "Jade cash reports have been uploaded to the FTP server");
        }

        return responseMap;
    }
}
