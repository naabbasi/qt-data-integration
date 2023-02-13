package com.qterminals.generate;

import com.qterminals.config.DBManager;
import com.qterminals.constant.SequenceType;
import com.qterminals.dto.Sequence;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class JadeCashInvoiceGenerator {
    private long counter = 0;
    private Path directoryPath;
    private List<Path> getGeneratedFiles = new ArrayList<>();

    public Boolean generate(Path directoryPath, String cashInvoiceSQLFileName, String cashDistributionSQLFileName, String cashReceiptSQLFileName) {
        DBManager instance = DBManager.getInstance();

        try {

            Connection connection = instance.getConnection();
            this.directoryPath = directoryPath;

            //Get last sequence
            Sequence lastSequence = getLastSequence(instance);

            instance.executeQuery(connection, "SET @row_number = '" + lastSequence.getSequenceCounter() + "'", Map.of());
            boolean interfaceLinesGenerated = generateJadeLines(cashInvoiceSQLFileName, instance);

            if (interfaceLinesGenerated) {
                instance.executeQuery(connection, "SET @row_number = '" + lastSequence.getSequenceCounter() + "'", Map.of());
                boolean interfaceDistributionGenerated = generateJadeDistributions(cashDistributionSQLFileName, instance);

                if (interfaceDistributionGenerated) {
                    boolean interfaceReceiptGenerated = generateJadeReceipts(cashReceiptSQLFileName, instance);

                    this.updateLastSequence(instance, lastSequence.getSequenceCounter());
                    return interfaceReceiptGenerated;
                }
            }
        } catch (IOException e) {
            log.error("generate(...) ", e);
            return false;
        } catch (SQLException e) {
            log.error("generate(...) ", e);
            return false;
        } finally {
            instance.closeConnection();
        }

        return true;
    }

    private boolean generateJadeLines(String cashInvoiceSQLFileName, DBManager instance) {
        try {
            Connection connection = instance.getConnection();
            ResultSet resultSet = instance.getResultSet(connection, cashInvoiceSQLFileName, Map.of());

            List<String> rows = new ArrayList<>();
            while (resultSet.next()) {
                StringBuilder row = new StringBuilder();
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    row.append(resultSet.getString(i));
                    if (i < (resultSetMetaData.getColumnCount())) {
                        row.append(",");
                    }
                /*
                int type = resultSetMetaData.getColumnType(i);
                if (type == Types.VARCHAR || type == Types.CHAR) {
                    System.out.println(resultSet.getString(i));
                } else {
                    System.out.println(resultSet.getLong(i));
                }*/
                }
                row.append("\n");
                rows.add(row.toString());
                this.counter = this.counter + 1;
            }

            resultSet.close();

            writeFile("CA_RaInterfaceLinesAll.csv", rows);
        } catch (IOException e) {
            log.error("generateJadeLines(...) ", e);
            return false;
        } catch (SQLException e) {
            log.error("generateJadeLines(...) ", e);
            return false;
        }

        return true;
    }

    private boolean generateJadeDistributions(String cashDistributionSQLFileName, DBManager instance) {
        try {
            Connection connection = instance.getConnection();
            ResultSet resultSet = instance.getResultSet(connection, cashDistributionSQLFileName, Map.of());

            List<String> rows = new ArrayList<>();
            while (resultSet.next()) {
                StringBuilder row = new StringBuilder();
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    row.append(resultSet.getString(i));
                    if (i < (resultSetMetaData.getColumnCount())) {
                        row.append(",");
                    }
                }

                row.append("\n");
                rows.add(row.toString());
            }

            resultSet.close();

            writeFile("CA_RaInterfaceDistributionsAll.csv", rows);
        } catch (IOException e) {
            log.error("generateJadeDistributions(...) ", e);
            return false;
        } catch (SQLException e) {
            log.error("generateJadeDistributions(...) ", e);
            return false;
        }

        return true;
    }

    private boolean generateJadeReceipts(String cashReceiptSQLFileName, DBManager instance) {
        try {
            Connection connection = instance.getConnection();
            ResultSet resultSet = instance.getResultSet(connection, cashReceiptSQLFileName, Map.of());
            String header = "BusinessUnit,ReceiptMethod,RemiitanceBankAccountNumber,ReceiptNumber,ReceiptAmount,ReceiptDate," +
                    "AccountingDate,CustomerName,CustomerAccountNumber,TransactionNumber,ApplyDate,AppliedAmount,Comments," +
                    "receiptLocation,actualCustomerName,chequeTtNumber,bankDrawnOn,referenceDocNo,voyageNumber,activity," +
                    "department,costCenter,creditCardAuthonicationNumber,posAuthonicationNumber,createdBy,issuerName,issueDate\n";

            List<String> rows = new ArrayList<>();
            rows.add(header);

            while (resultSet.next()) {
                StringBuilder row = new StringBuilder();
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    row.append(resultSet.getString(i));
                    if (i < (resultSetMetaData.getColumnCount())) {
                        row.append(",");
                    }
                }

                row.append("\n");
                rows.add(row.toString());
            }

            resultSet.close();

            writeFile("QTJadeReceiptExtracts.csv", rows);
        } catch (IOException e) {
            log.error("generateJadeReceipts(...) ", e);
            return false;
        } catch (SQLException e) {
            log.error("generateJadeReceipts(...) ", e);
            return false;
        }

        return true;
    }

    private Boolean updateLastSequence(DBManager instance, Long lastDBCounter) throws SQLException {
        Connection connection = instance.getConnection();
        Long updatedCounter = lastDBCounter + this.getCounter();
        int rows = instance.execute(connection, "update sequence set sequence_counter = ? where sequence_type = ?",
                Map.of(1, updatedCounter, 2, SequenceType.JADE_CASH_INVOICE.toString())
        );

        log.info("updateLastSequence(...) sequence counter is updated to {}", updatedCounter);
        return rows > 0 ? true : false;
    }

    private Sequence getLastSequence(DBManager instance) throws IOException, SQLException {
        Connection connection = instance.getConnection();
        ResultSet getLastSequence = instance.getResultSetByQuery(connection, "select * from sequence where sequence_type = ?", Map.of(1, SequenceType.JADE_CASH_INVOICE.toString()));
        Sequence sequence = new Sequence();
        while (getLastSequence.next()) {
            sequence.setGenericKey(getLastSequence.getLong(1));
            sequence.setSequenceFormat(getLastSequence.getString(2));
            sequence.setSequenceCounter(getLastSequence.getLong(3));
            sequence.setMaximumDigits(getLastSequence.getLong(4));
            sequence.setSequenceType(SequenceType.valueOf(getLastSequence.getString(5)));
        }

        log.info("getLastSequence(...) sequence counter is {}", sequence.getSequenceCounter());
        return sequence;
    }

    private void writeFile(String fileName, List<String> rows) throws IOException {
        log.info("writeFile(...) writing fileName: {}", fileName);
        Path writeLines = this.directoryPath.resolve(Path.of(fileName));
        OutputStream outputStream = Files.newOutputStream(writeLines, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

        for (String row : rows) {
            outputStream.write((row).getBytes(StandardCharsets.UTF_8));
        }

        outputStream.close();

        getGeneratedFiles.add(writeLines);
    }

    private Long getCounter() {
        return this.counter;
    }

    public List<Path> getGetGeneratedFiles() {
        return getGeneratedFiles;
    }
}
