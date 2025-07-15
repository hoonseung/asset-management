package com.sewon.notification.dto;

import com.sewon.notification.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class NotificationResult {

    private Long id;
    private Long accountId;
    private String content;
    @Setter
    private boolean isRead;
    private String notifyTime;


    public static NotificationResult from(Notification notification) {
        return new NotificationResult(
            notification.getId(),
            notification.getAccount().getId(),
            notification.getContent(),
            notification.getIsRead(),
            notification.getNotificationDate().toString()
        );
    }
}
