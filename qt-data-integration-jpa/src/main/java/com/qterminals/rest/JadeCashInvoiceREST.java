package com.qterminals.rest;

import com.qterminals.ftp.FTPUtils;
import com.qterminals.helper.JadeCashHelper;
import com.qterminals.services.JadeCashInvoiceService;
import com.qterminals.util.AppHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

@Slf4j
@RequestMapping("/api/jade-cash")
@RestController
public class JadeCashInvoiceREST {
    private AppHelper appHelper;
    private JadeCashInvoiceService jadeCashInvoiceService;
    private FTPUtils ftpUtils;

    public JadeCashInvoiceREST(AppHelper appHelper, JadeCashInvoiceService jadeCashInvoiceService, FTPUtils ftpUtils) {
        this.appHelper = appHelper;
        this.jadeCashInvoiceService = jadeCashInvoiceService;
        this.ftpUtils = ftpUtils;
    }

    @GetMapping(value = "/api/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Object> streamDataFlux() {
        return Flux.interval(Duration.ofSeconds(1)).map(i -> "Data stream line - " + i);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity upload(@RequestParam("jadeCashExcels") MultipartFile[] jadeCashExcels, @Value("${file.upload.directory}") String uploadDirectory) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Path directoryPath = Path.of(uploadDirectory + "/" + simpleDateFormat.format(new Date()) + "/");
        List<Path> uploadedFilesPath = new ArrayList<>();
        String fileName = null;
        final Map<String, Object> responseMap = new HashMap<>();

        boolean directoryExists = this.appHelper.createDirectory(simpleDateFormat.format(new Date()), uploadDirectory);

        for (MultipartFile jadeCashExcel : jadeCashExcels) {
            try {
                if (directoryExists) {
                    fileName = jadeCashExcel.getOriginalFilename();
                    Path path = Path.of(directoryPath.toAbsolutePath().toString() + "/" + fileName);
                    byte[] fileBytes = jadeCashExcel.getBytes();

                    OutputStream outputStream = Files.newOutputStream(path);
                    outputStream.write(fileBytes);
                    outputStream.close();
                    uploadedFilesPath.add(path);
                }
            } catch (IOException e) {
                log.error("upload(...) ", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }

        responseMap.put("fileStatus", jadeCashExcels.length > 1 ? "Files have been uploaded successfully" : (fileName + " has been uploaded successfully"));
        JadeCashHelper jadeCashHelper = new JadeCashHelper(this.jadeCashInvoiceService, this.ftpUtils);

        Map<String, Object> excelParsingResponse = jadeCashHelper.parseExcelFile(directoryPath, uploadedFilesPath, uploadedFilesPath.size() == 1 ? fileName : "MergeAllExcels.csv");
        responseMap.putAll(excelParsingResponse);

        return ResponseEntity.ok(responseMap);
    }
}
