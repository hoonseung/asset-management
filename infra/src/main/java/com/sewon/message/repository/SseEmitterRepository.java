package com.sewon.message.repository;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Repository
public class SseEmitterRepository {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
    private static final Long TIMEOUT = 60 * 60 * 1000L; // 1시간

    public SseEmitter addEmitter(Long accountId) {
        SseEmitter emitter = new SseEmitter(TIMEOUT);
        emitters.put(accountId, emitter);
        emitter.onCompletion(() -> emitters.remove(accountId)); // 연결이 종료 (1시간 뒤) 되고 삭제 콜백 실행
        emitter.onTimeout(() -> emitters.remove(accountId));
        return emitter;
    }

    public Optional<SseEmitter> get(Long accountId) {
        return Optional.ofNullable(emitters.get(accountId));
    }

    public void sendTo(Long accountId, Object data) {
        get(accountId).ifPresent(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                    .name("notification")
                    .data(data));
                log.info("connected");
            } catch (IOException e) {
                log.error("occurs emit error: {}", e.getMessage());
                emitters.remove(accountId);
            }
        });
    }
}
