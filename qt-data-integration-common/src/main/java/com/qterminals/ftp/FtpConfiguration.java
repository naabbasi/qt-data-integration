package com.qterminals.ftp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;

@Slf4j
@Configuration
class FtpConfiguration {
    private Environment environment;

    public FtpConfiguration(Environment environment) {
        this.environment = environment;
    }

    /*@Bean
    InitializingBean initializingBean(FtpRemoteFileTemplate template) {
        return () -> template
                .execute(session -> {
                    log.info("Host port: {}", session.getHostPort());

                    if(!session.exists(this.environment.getProperty(FTP_WORKING_DIRECTORY))){
                        session.mkdir(FTP_WORKING_DIRECTORY);
                    }
                    return null;
                });
    }*/

    @Bean
    DefaultFtpSessionFactory defaultFtpSessionFactory(@Value("${ftp.username}") String username, @Value("${ftp.password}") String pw,
                                                      @Value("${ftp.host}") String host, @Value("${ftp.port}") int port) {

        DefaultFtpSessionFactory defaultFtpSessionFactory = new DefaultFtpSessionFactory();
        defaultFtpSessionFactory.setPassword(pw);
        defaultFtpSessionFactory.setUsername(username);
        defaultFtpSessionFactory.setHost(host);
        defaultFtpSessionFactory.setPort(port);
        defaultFtpSessionFactory.setClientMode(2);

        return defaultFtpSessionFactory;
    }

    @Bean
    FtpRemoteFileTemplate ftpRemoteFileTemplate(DefaultFtpSessionFactory dsf) {
        return new FtpRemoteFileTemplate(dsf);
    }
}