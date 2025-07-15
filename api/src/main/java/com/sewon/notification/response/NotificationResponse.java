package com.sewon.notification.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sewon.notification.dto.NotificationResult;
import java.util.List;

public record NotificationResponse(
    @JsonProperty("list")
    List<NotificationResult> results
) {

}
