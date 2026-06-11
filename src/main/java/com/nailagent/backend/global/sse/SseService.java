package com.nailagent.backend.global.sse;

import com.nailagent.backend.domain.reservation.entity.Reservation;
import com.nailagent.backend.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SseService {

    private final ReservationRepository reservationRepository;

    private SseEmitter emitter;

    public SseEmitter connect() {
        if (emitter != null) {
            emitter.complete();
        }
        SseEmitter newEmitter = new SseEmitter(Long.MAX_VALUE);
        emitter = newEmitter;
        newEmitter.onCompletion(() -> { if (emitter == newEmitter) emitter = null; });
        newEmitter.onTimeout(() -> { if (emitter == newEmitter) emitter = null; });

        try {
            newEmitter.send(SseEmitter.event()
                    .name("connected")
                    .data("SSE 연결 성공"));
        } catch (IOException e) {
            log.error("SSE 초기 이벤트 전송 실패", e);
            newEmitter.completeWithError(e);
        }

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
                            "customer_name", customerName,
                            "waiting", waiting
                    )));
        } catch (IOException e) {
            log.error("SSE 전송 실패", e);
            if (emitter == current) emitter = null;
        }
    }

    @Scheduled(cron = "0 0 8 * * *", zone = "Asia/Seoul")
    public void sendDailyBriefing() {
        List<Reservation> reservations = reservationRepository.findAllByReserveDate(LocalDate.now());

        List<Map<String, Object>> items = reservations.stream()
                .map(r -> Map.<String, Object>of(
                        "time", r.getReserveTime(),
                        "name", r.getName(),
                        "service", r.getService(),
                        "designer", r.getDesigner() != null ? r.getDesigner() : "사장님"
                ))
                .collect(Collectors.toList());

        Map<String, Object> briefing = Map.of(
                "total", reservations.size(),
                "reservations", items
        );

        SseEmitter current = emitter;
        if (current == null) {
            log.warn("SSE 연결된 클라이언트 없음 - 브리핑 전송 불가");
            return;
        }
        try {
            current.send(SseEmitter.event()
                    .name("daily_briefing")
                    .data(briefing));
            log.info("오늘의 예약 브리핑 전송 완료 - 총 {}건", reservations.size());
        } catch (IOException e) {
            log.error("브리핑 SSE 전송 실패", e);
            if (emitter == current) emitter = null;
        }
    }

    @Scheduled(fixedDelay = 30000)
    public void sendHeartbeat() {
        SseEmitter current = emitter;
        if (current == null) return;
        try {
            current.send(SseEmitter.event()
                    .comment("heartbeat"));
        } catch (IOException e) {
            log.warn("SSE heartbeat 전송 실패 - 연결 해제");
            if (emitter == current) emitter = null;
        }
    }
}
