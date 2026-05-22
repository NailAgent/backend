package com.nailagent.backend.global.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public class SseService {

    private SseEmitter emitter;

    public SseEmitter connect() {
        if (emitter != null) {
            emitter.complete();
        }
        SseEmitter newEmitter = new SseEmitter(Long.MAX_VALUE);
        emitter = newEmitter;
        newEmitter.onCompletion(() -> { if (emitter == newEmitter) emitter = null; });
        newEmitter.onTimeout(() -> { if (emitter == newEmitter) emitter = null; });
        return newEmitter;
    }

    public void send(String customerName, Boolean waiting) {
        SseEmitter current = emitter;
        if (current == null) {
            log.warn("SSE 연결된 클라이언트 없음 - 알림 전송 불가");
            return;
        }
        try {
            current.send(SseEmitter.event()
                    .name("inquiry")
                    .data(Map.of(
                            "customerName", customerName,
                            "waiting", waiting
                    )));
        } catch (IOException e) {
            log.error("SSE 전송 실패", e);
            if (emitter == current) emitter = null;
        }
    }
}
