package com.hammad.meetelligence.Scheduler;

import com.hammad.meetelligence.Entity.ActionItem;
import com.hammad.meetelligence.Entity.ReminderHistory;
import com.hammad.meetelligence.Repository.ReminderHistoryRepository;
import com.hammad.meetelligence.Service.ActionItemService;
import com.hammad.meetelligence.Service.DiscordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReminderScheduler {

    private final ActionItemService actionItemService;
    private final ReminderHistoryRepository reminderHistoryRepository;
    private final DiscordService discordService;

    @Autowired
    public ReminderScheduler(ActionItemService actionItemService, ReminderHistoryRepository reminderHistoryRepository, DiscordService discordService) {
        this.actionItemService = actionItemService;
        this.reminderHistoryRepository = reminderHistoryRepository;
        this.discordService = discordService;
    }

    // Use 86400000 (i.e a day) instead of 60000 (1 minute) in real applications
    // Better yet, use @Scheduled(cron = "0 0 9 * * *") (i.e : 09:00 am everyday)
    @Scheduled(fixedRate = 60000)
    public void checkOverdueItems() {
        List<ActionItem> overdueItems = actionItemService.findAllOverdueActionItems();

        for (ActionItem item : overdueItems) {
            System.out.println("processing item: " + item);

            boolean recentlySent = reminderHistoryRepository
                    .existsByActionItemIdAndSentAtAfter(item.getId(), LocalDateTime.now().minusHours(24));

            if (recentlySent) continue;

            try {
                discordService.sendReminder(
                        """
                        Reminder: %s
                        
                        Assigned To: %s
                        
                        Due Date: %s
                        """
                                .formatted(
                                        item.getTask(),
                                        item.getAssignee(),
                                        item.getDueDate()
                                )
                );

                ReminderHistory history = ReminderHistory.builder()
                        .actionItem(item)
                        .sentAt(LocalDateTime.now())
                        .success(true)
                        .build();
                reminderHistoryRepository.save(history);
            } catch (Exception e) {
                ReminderHistory history = ReminderHistory.builder()
                        .actionItem(item)
                        .sentAt(LocalDateTime.now())
                        .success(false)
                        .build();
            }
        }

        System.out.println("Found " + overdueItems.size() + " overdue items");
    }
}
