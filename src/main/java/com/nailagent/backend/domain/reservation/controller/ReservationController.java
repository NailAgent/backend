package com.nailagent.backend.domain.reservation.controller;

import com.nailagent.backend.domain.reservation.dto.ReservationListResponse;
import com.nailagent.backend.domain.reservation.dto.ScheduleResponse;
import com.nailagent.backend.domain.reservation.service.ReservationService;
import com.nailagent.backend.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Reservation", description = "예약 관련 API")
@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "예약 스케줄 조회", description = "특정 날짜의 영업시간과 예약된 시간대 목록을 반환합니다.")
    @GetMapping("/schedule")
    public ResponseEntity<ApiResponse<ScheduleResponse>> getSchedule(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ResponseEntity.ok(ApiResponse.ok(reservationService.getSchedule(date)));
    }

    @Operation(summary = "전체 예약 목록 조회", description = "페이지네이션을 통해 전체 예약 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<ReservationListResponse>> getReservations(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        return ResponseEntity.ok(ApiResponse.ok(reservationService.getReservations(page, size)));
    }
}
