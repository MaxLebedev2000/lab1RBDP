package com.example.springboot.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class RemindJob implements Job {
    private final ReminderService reminderService;

    public RemindJob(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @Override
    public void execute(JobExecutionContext context)  {
        reminderService.setRemindsToModerators();
    }
}