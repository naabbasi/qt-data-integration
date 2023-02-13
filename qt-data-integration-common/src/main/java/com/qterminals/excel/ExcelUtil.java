package com.qterminals.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
    public Sheet readFile(String path) {
        Sheet sheet = null;
        try {
            Workbook workbook = new XSSFWorkbook(path);
            sheet = workbook.getSheetAt(0);
            workbook.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return sheet;
    }

    public List<String> parse(Sheet sheet) {
        List<String> rows = new ArrayList<>();
        Map<Integer, String> skipColumns = Map.of(2,"", 3,"::SKIP_AMOUNT::");

        if (sheet != null) {
            for (Row row : sheet) {
                StringBuilder rowAsString = new StringBuilder();

                if (row.getRowNum() > 2) {
                    BigDecimal bigDecimal = BigDecimal.ZERO;
                    for (Cell cell : row) {
                        String skipToken = skipColumns.get(cell.getColumnIndex());
                        if(skipToken != null && !skipToken.equals("")){
                            //cell.setCellFormula(cell.getCellFormula());
                            rowAsString.append(skipToken+ ",");
                        } else if(cell.getColumnIndex() > 2){
                            BigDecimal value = new BigDecimal("" + cell.getNumericCellValue()).setScale(2, RoundingMode.DOWN);
                            bigDecimal = bigDecimal.add(value);
                            rowAsString.append(value+ ",");
                        } else if(skipToken == null){
                            rowAsString.append(cell+ ",");
                        }
                    }

                    String replaceToken = rowAsString.toString().replace(skipColumns.get(3), bigDecimal.toString());
                    //TODO: Will update later
                    //for(Map.Entry<Integer, String> token : skipColumns)

                    rows.add(replaceToken);
                }
            }
        }

        return rows;
    }

    public void convertExcelToCSV(Sheet sheet, Path file){
        try {
            OutputStream outputStream = Files.newOutputStream(file, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            for(String row : getRawCSV(sheet)){
                outputStream.write(row.getBytes(StandardCharsets.UTF_8));
            }
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getRawCSV(Sheet sheet){
        List<String> rows = new ArrayList<>();

        for(String row : this.parse(sheet)){
            rows.add(row + "\n");
        }

        return rows;
    }

    public void writeCSV(List<String> convertedExcelsAsCSV, Path csvFile) {
        try {
            OutputStream outputStream = Files.newOutputStream(csvFile, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            for(String row : convertedExcelsAsCSV){
                outputStream.write(row.getBytes(StandardCharsets.UTF_8));
            }
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ExcelUtil excelUtil = new ExcelUtil();
        Sheet sheet = excelUtil.readFile("C:\\temp\\01-04-Jun-2022 JAD Report.xlsx");

        try {
            OutputStream outputStream = Files.newOutputStream(Paths.get("C:\\temp\\01-04-Jun-2022 JAD Report.csv"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            for (String row : excelUtil.parse(sheet)) {
                outputStream.write(row.getBytes(StandardCharsets.UTF_8));
                outputStream.write("\n".getBytes(StandardCharsets.UTF_8));
            }
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}