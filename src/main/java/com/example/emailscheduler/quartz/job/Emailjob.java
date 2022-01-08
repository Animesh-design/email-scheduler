package com.example.emailscheduler.quartz.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static com.mchange.net.SmtpUtils.sendMail;

@Component
public class Emailjob extends QuartzJobBean {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailProperties mailProperties;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        JobDataMap jobDataMap = context.getMergedJobDataMap();

        String subject = jobDataMap.getString("subject");
        String body = jobDataMap.getString("body");
        String recipientMail = jobDataMap.getString("email");

        sendMail(subject, body, mailProperties.getUsername(), recipientMail);
    }

    private void sendMail(String subject, String body, String fromMail, String recipientMail){

        try {

            Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", "true");

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
            messageHelper.setSubject(subject);
            messageHelper.setText(body, true);
            messageHelper.setTo(recipientMail);
            messageHelper.setFrom(fromMail);

            mailSender.send(message);

        }catch (Exception e){
            System.out.println("Error Occurred in Sending Mail "+ e);
        }

    }
}
