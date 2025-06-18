package com.ept.Scheduler;

import com.ept.Service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskEmailScheduler {

    @Autowired
    private ReminderService reminderService;

    // Runs every day at 8 AM
    @Scheduled(cron = "0 0 8 * * ?", zone = "Asia/Kolkata")
    //@Scheduled(cron = "0 47 13 * * ?", zone = "Asia/Kolkata")
    public void runDailyTaskReminder() {
        reminderService.sendDailyTaskEmailsToAllEmployees();
    }
}

