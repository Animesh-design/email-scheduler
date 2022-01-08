package com.example.emailscheduler.controller;

import com.example.emailscheduler.payload.EmailRequest;
import com.example.emailscheduler.quartz.job.Emailjob;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;

import java.util.UUID;

public class EmailSchedulerController {


    private JobDetail buildJobDetail(EmailRequest scheduleEmailRequest) {

        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("email", scheduleEmailRequest.getEmail()); // Comment is added
        jobDataMap.put("subject", scheduleEmailRequest.getSubject());
        jobDataMap.put("body", scheduleEmailRequest.getBody());

        return JobBuilder.newJob(Emailjob.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }
}
