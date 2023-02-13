package com.qterminals.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.file.remote.session.Session;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Component
public class FTPUtils {
    private FtpRemoteFileTemplate ftpRemoteFileTemplate;
    private String ftpDirectory;

    public FTPUtils(FtpRemoteFileTemplate ftpRemoteFileTemplate, @Value("${ftp.working.directory}") String ftpDirectory) {
        this.ftpRemoteFileTemplate = ftpRemoteFileTemplate;
        this.ftpDirectory = ftpDirectory;
    }

    public void uploadFile(Path path) {

        try {
            InputStream inputStream = Files.newInputStream(path);
            getFtpSession().write(inputStream, changeWorkingDirectory() + "/" + path.getFileName());
            inputStream.close();
        } catch (IOException ioException){
            log.error("uploadFile(...) ", ioException);
        }
    }

    public void uploadFile(Path path, String ftpDirectory) {

        try {
            InputStream inputStream = Files.newInputStream(path);
            getFtpSession().write(inputStream, ftpDirectory + "/" + path.getFileName());
            inputStream.close();
        } catch (IOException ioException){
            log.error("uploadFile(...) ", ioException);
        }
    }

    public byte[] downloadFile(String fileName, String ftpDirectory) {

        try {
            //TODO: Multi user testing required
            Path temp = Path.of("/temp/" + fileName);
            OutputStream outputStream = Files.newOutputStream(temp);
            getFtpSession().read(ftpDirectory + fileName, outputStream);
            outputStream.close();

            InputStream inputStream = Files.newInputStream(temp);
            byte[] fileAsByteArray = new byte[inputStream.available()];
            inputStream.read(fileAsByteArray);
            inputStream.close();
            return fileAsByteArray;
        } catch (IOException ioException){
            log.error("downloadFile(...) ", ioException);
        }

        return new byte[0];
    }

    public FTPFile[] list(String directory){
        try{
            FTPFile[] ftpFiles;
            if(directory.isBlank()){
                ftpFiles = getFtpSession().list("/");
            } else {
                ftpFiles = getFtpSession().list(directory);
            }

            return ftpFiles;
        }catch (IOException ioException){
            log.error("list(...) ", ioException);
        }

        return new FTPFile[0];
    }

    public boolean createDirectory(String directoryName) {
        String workingDirectory = changeWorkingDirectory();

        try {
            if(getFtpSession().mkdir(workingDirectory + "/" + directoryName)){
                return true;
            }
        } catch (IOException e) {
            log.error("createDirectory(...) ", e);
        }

        return false;
    }

    public String changeWorkingDirectory() {
        try {
            if(!getFtpSession().exists(this.ftpDirectory)){
                getFtpSession().mkdir(this.ftpDirectory);
            }
        } catch (IOException e) {
            log.error("changeWorkingDirectory(...) ", e);
        }

        return this.ftpDirectory;
    }

    public boolean checkDirectoryExists(String directoryPath) {
        try {
            return getFtpSession().exists(directoryPath);
        } catch (IOException e) {
            log.error("checkDirectoryExists(...) {} is not exists", directoryPath);
            return false;
        }
    }

    public boolean checkFileExists(String filePath) {
        try {
            return getFtpSession().exists(filePath);
        } catch (IOException e) {
            log.error("checkDirectoryExists(...) {} is not exists", filePath);
            return false;
        }
    }

    public Session<FTPFile> getFtpSession(){
        if(this.ftpRemoteFileTemplate == null)
            throw new RuntimeException("Please initialized the FTP Session");

        return this.ftpRemoteFileTemplate.getSession();
    }

    private String getFileType(int fileType){
        if(fileType == 0)
            return "FILE";

        if(fileType == 1)
            return "DIRECTORY";

        return "UNKNOWN";
    }
}