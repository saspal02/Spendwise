package com.spendwise.schedule;

import com.spendwise.dto.JobStatusDto;
import com.spendwise.model.AiParsingTask;
import com.spendwise.model.Status;
import com.spendwise.service.ai.AiService;
import com.spendwise.service.ai.parsetask.AiParseTaskService;
import com.spendwise.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.PriorityQueue;

@Component
@RequiredArgsConstructor
public class AiTaskScheduler {

    private final AiParseTaskService aiParseTaskService;
    private final AiService aiService;
    private final NotificationService notificationService;

    private final PriorityQueue<AiParsingTask> taskQueue = new PriorityQueue<>(Comparator.comparing(AiParsingTask::getCreatedAt));

    @Scheduled(fixedRate = 5000)
    void scheduleAiTask() {
        if (taskQueue.isEmpty()) {
            final var tasks = aiParseTaskService.getPendingTasks(Status.PENDING);
            taskQueue.addAll(tasks);
        }

        if (!taskQueue.isEmpty()) {
            final var aiParsingTask = taskQueue.remove();
            aiParsingTask.setStatus(Status.PROCESSING);
            aiParseTaskService.save(aiParsingTask);

            notificationService.send(
                    JobStatusDto.of(aiParsingTask.getId().toString(),
                    aiParsingTask.getStatus().name())
            );

            aiService.parse(aiParsingTask);
        }
    }
}
