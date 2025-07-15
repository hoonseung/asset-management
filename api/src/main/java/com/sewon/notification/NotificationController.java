package com.sewon.notification;


import com.sewon.common.response.ApiResponse;
import com.sewon.message.repository.SseEmitterRepository;
import com.sewon.notification.application.NotificationService;
import com.sewon.notification.dto.NotificationResult;
import com.sewon.notification.response.NotificationResponse;
import com.sewon.security.model.auth.AuthUser;
import com.sewon.security.service.JwtTokenHandler;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@RequestMapping("/notifications")
@RestController
public class NotificationController {

    private final SseEmitterRepository sseEmitterRepository;
    private final JwtTokenHandler tokenHandler;

    private final NotificationService notificationService;

    @GetMapping(value = "/connect/{accountId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connectClient(
        @PathVariable("accountId") Long accountId,
        @RequestParam("token") String token
    ) {
        if (tokenHandler.isValidAccessToken(token)) {
            SseEmitter emitter = sseEmitterRepository.addEmitter(accountId);
            try {
                emitter.send(
                    SseEmitter.event()
                        .name("connect")
                        .data("connected")
                        .reconnectTime(3000)
                );
            } catch (IOException e) {
                emitter.completeWithError(e);
                return ResponseEntity.internalServerError().build();
            }
            return ResponseEntity.ok(emitter);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<NotificationResponse>> getNotifications(
        @AuthenticationPrincipal AuthUser authUser
    ) {
        List<NotificationResult> notifications = notificationService.getNotifications(
            authUser.getId());

        return ResponseEntity.ok(ApiResponse.ok(new NotificationResponse(notifications))
        );
    }

    @PostMapping("/read/{notificationId}")
    public ResponseEntity<ApiResponse<Void>> readNotification(
        @PathVariable(name = "notificationId") Long id,
        @AuthenticationPrincipal AuthUser authUser
    ) {
        notificationService.markRead(authUser.getId(), id);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
