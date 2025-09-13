package com.zn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.nursing.host}")
    private String nursingHost;

    @Value("${spring.mail.nursing.port}")
    private int nursingPort;

    @Value("${spring.mail.nursing.username}")
    private String nursingUsername;

    @Value("${spring.mail.nursing.password}")
    private String nursingPassword;

    @Value("${spring.mail.nursing.properties.mail.smtp.auth}")
    private String nursingAuth;

    @Value("${spring.mail.nursing.properties.mail.smtp.ssl.enable}")
    private String nursingSsl;

    @Value("${spring.mail.nursing.properties.mail.smtp.starttls.enable}")
    private String nursingStarttls;

    @Bean(name = "nursingMailSender")
    public JavaMailSender nursingMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(nursingHost);
        mailSender.setPort(nursingPort);
        mailSender.setUsername(nursingUsername);
        mailSender.setPassword(nursingPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", nursingAuth);
        props.put("mail.smtp.ssl.enable", nursingSsl);
        props.put("mail.smtp.starttls.enable", nursingStarttls);

        return mailSender;
    }
}
