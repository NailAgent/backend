package com.nailagent.backend.global.sse;

import com.nailagent.backend.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Tag(name = "SSE", description = "실시간 알림 관련 API")
@RestController
@RequestMapping("/api/v1/sse")
@RequiredArgsConstructor
public class SseController {

    private final SseService sseService;

    @Operation(summary = "SSE 연결", description = "사장님 웹에서 실시간 알림을 수신하기 위한 SSE 연결을 맺습니다.")
    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "keep-alive");
        return sseService.connect();
    }

    @Operation(summary = "알림 전송", description = "AI 서버가 LLM 응답 불가 시 사장님 웹으로 알림을 전송합니다.")
    @PostMapping("/notify")
    public ResponseEntity<ApiResponse<Void>> notify(@Valid @RequestBody SseNotifyRequest request) {
        sseService.send(request.getCustomerName(), request.getWaiting());
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
