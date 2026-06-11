package com.spendwise.service.notification;

import com.spendwise.dto.JobStatusDto;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {
    void send(JobStatusDto jobStatus);
    void openConnection(String string);
    SseEmitter get(String jobId);
    void closeConnection(String jobId);
}
