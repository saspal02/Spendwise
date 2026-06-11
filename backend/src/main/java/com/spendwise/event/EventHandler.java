package com.spendwise.event;

import com.spendwise.dto.JobStatusDto;
import com.spendwise.mapper.TransactionMapper;
import com.spendwise.service.UserConfigService.UserConfigService;
import com.spendwise.service.ai.AiParseResult;
import com.spendwise.service.ai.parsetask.AiParseTaskService;
import com.spendwise.service.category.CategoryService;
import com.spendwise.service.notification.NotificationService;
import com.spendwise.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventHandler {

    private final NotificationService notificationService;
    private final TransactionService transactionService;
    private final UserConfigService userConfigService;
    private final AiParseTaskService aiParseTaskService;
    private final CategoryService categoryService;
    private final ObjectMapper objectMapper;

    @Async
    @EventListener(AiParsingTaskCompleted.class)
    public void notifyClient(AiParsingTaskCompleted event) {
        log.info("Ai parsing task completed. Notifying client...");
        final var jobStatus = JobStatusDto.of(
                event.jobId().toString(),
                event.task().getStatus().name());
        notificationService.send(jobStatus);
        notificationService.closeConnection(event.jobId().toString());
    }

    @Async
    @EventListener(AiParsingTaskCompleted.class)
    public void saveResultAsTxn(AiParsingTaskCompleted event) {
        log.info("Ai parsing task completed. Converting and saving data into DB");

        final var task = aiParseTaskService.getIdWithAppUser(event.jobId());

        final var appUserId = task.getAppUser().getId();

        //Get default Payment mode and account
        final var userConfig = userConfigService.getByUserId(appUserId);

        final var aiParseResult = objectMapper.readValue(event.task().getContent(), AiParseResult.class);

        final var category = categoryService.getByName(aiParseResult.category());

        final var requestDto = TransactionMapper.INSTANCE.fromAiParseTask(
                aiParseResult, // Task -> Source
                userConfig.getDefaultPaymentMode().getId(), //Payment Mode id
                userConfig.getDefaultaccount().getId(), // Account id
                category.getId() // Category Id
        );

        transactionService.saveTransaction(appUserId, requestDto);
    }

    @EventListener(AiParsingTaskCreated.class)
    public void openConnection(AiParsingTaskCreated event) {
        log.info("Ai parsing task created. Opening connection...");
        notificationService.openConnection(event.jobId().toString());
    }



}
