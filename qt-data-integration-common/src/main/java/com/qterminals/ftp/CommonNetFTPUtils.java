package com.qterminals.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CommonNetFTPUtils {
    private static FTPClient ftpClient;

    private CommonNetFTPUtils(){}

    private CommonNetFTPUtils(FTPClient ftpClient){
        this.ftpClient = ftpClient;
    }

    public static class FTPBuilder {
        private String host;
        private String username;
        private String pwd;

        public FTPBuilder host(String host){
            this.host = host;
            return this;
        }

        public FTPBuilder username(String username){
            this.username = username;
            return this;
        }

        public FTPBuilder password(String pwd){
            this.pwd = pwd;
            return this;
        }

        public CommonNetFTPUtils build() {
            try{
                if(ftpClient == null){
                    ftpClient = new FTPClient();
                    //FTP_CLIENT.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
                    ftpClient.connect(this.host, 21);
                    showServerReply();

                    int replyCode = ftpClient.getReplyCode();
                    if (!FTPReply.isPositiveCompletion(replyCode)) {
                        System.out.println("Operation failed. Server reply code: " + replyCode);
                    }

                    boolean success = ftpClient.login(this.username, this.pwd);
                    showServerReply();

                    if (!success) {
                        System.out.println("Could not login to the server");
                    } else {
                        System.out.println("Connect with server successfully");
                    }
                }
            }catch (Exception exception){
                exception.printStackTrace();
            }

            return new CommonNetFTPUtils(ftpClient);
        }
    }

    private static void showServerReply() {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
    }

    public void uploadFile(FTPClient ftpClient, String localFileFullName, String fileName, String hostDir) throws IOException {
        try (InputStream input = new FileInputStream(localFileFullName)) {
            ftpClient.storeFile(hostDir + fileName, input);
        }
    }

    public void list(FTPClient ftpClient, String hostDir) throws IOException {
        if(hostDir.isBlank()){
            ftpClient.list();
        }

        ftpClient.list(hostDir);
    }

    public boolean createDirectory(String directoryName) throws IOException {
        ftpClient.changeWorkingDirectory(directoryName);
        int returnCode = ftpClient.getReplyCode();
        if (returnCode == 550) {
            boolean status = ftpClient.makeDirectory(directoryName);
            showServerReply();
            return status;
        }

        return true;
    }

    public void changeWorkingDirectory(String directoryPath) throws IOException {
        ftpClient.changeWorkingDirectory(directoryPath);
        showServerReply();
    }

    public void listDirectories(String directoryPath) throws IOException {
        FTPFile[] directories = directoryPath == null || directoryPath.equals("") ? ftpClient.listDirectories() : ftpClient.listDirectories(directoryPath);
        for(FTPFile ftpFile : directories){
            System.out.println(ftpFile.toFormattedString());
        }
    }

    public boolean checkDirectoryExists(String directoryPath) throws IOException {
        ftpClient.changeWorkingDirectory(directoryPath);
        int returnCode = ftpClient.getReplyCode();
        if (returnCode == 550) {
            System.out.println(directoryPath + " is not exists");
            return false;
        }

        showServerReply();
        return true;
    }

    public boolean checkFileExists(String filePath) throws IOException {
        InputStream inputStream = ftpClient.retrieveFileStream(filePath);
        int returnCode = ftpClient.getReplyCode();
        if (inputStream == null || returnCode == 550) {
            return false;
        }

        showServerReply();
        return true;
    }

    public void disconnect() {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException f) {
                f.printStackTrace();
            }
        }
    }
}