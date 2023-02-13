package com.qterminals.csv;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.qterminals.dto.CsvTransfer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class CSVUtils<T> {
    public CsvTransfer<T> getJadeCashInvoiceCsvTransfer(Path tempCSVFile, Class<T> tClass) {
        Reader reader = null;
        try{
            ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
            ms.setType(tClass);

            reader = Files.newBufferedReader(tempCSVFile);
            CsvToBean cb = new CsvToBeanBuilder(reader)
                    .withType(tClass)
                    .withMappingStrategy(ms)
                    .build();

            CsvTransfer<T> csvTransfer = new CsvTransfer();
            csvTransfer.setCsvList(cb.parse());
            return csvTransfer;
        }catch (IOException ioException){
            log.error("getJadeCashInvoiceCsvTransfer(...) ", ioException);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }
}
