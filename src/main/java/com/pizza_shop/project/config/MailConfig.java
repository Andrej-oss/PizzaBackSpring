package com.pizza_shop.project.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@Slf4j
public class MailConfig {
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.protocol}")
    private String protocol;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String enable;
//    @Value("${mail.debug}")
//    private String debug;
    @Bean
    public JavaMailSender getMailSender(){
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setPort(port);
        mailSender.setProtocol(protocol);
        final Properties mailProperties = mailSender.getJavaMailProperties();
        mailProperties.setProperty("mail.transport.protocol", protocol);
        mailProperties.setProperty("mail.debug", "true");
        mailProperties.setProperty("mail.smtp.auth", auth);
        mailProperties.setProperty("mail.smtp.starttls.enable", enable);
        log.info("Handling mailSender in MailConfig " + mailSender.toString());
        return mailSender;
    };
}
