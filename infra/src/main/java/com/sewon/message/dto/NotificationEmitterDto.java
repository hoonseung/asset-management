package com.sewon.message.dto;

import java.time.LocalDateTime;

public record NotificationEmitterDto(
    String message,
    LocalDateTime notifyTime
) {

}
