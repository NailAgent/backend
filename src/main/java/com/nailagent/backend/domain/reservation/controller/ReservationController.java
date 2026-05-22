package com.nailagent.backend.domain.reservation.controller;

import com.nailagent.backend.domain.reservation.dto.Request.ReservationRequest;
import com.nailagent.backend.domain.reservation.dto.Request.ReservationUpdateRequest;
import com.nailagent.backend.domain.reservation.dto.Response.ReservationCreateResponse;
import com.nailagent.backend.domain.reservation.dto.Response.ReservationListResponse;
import com.nailagent.backend.domain.reservation.dto.Response.ScheduleResponse;
import com.nailagent.backend.domain.reservation.service.ReservationService;
import com.nailagent.backend.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "예약 생성", description = "새로운 예약을 등록합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<ReservationCreateResponse>> createReservation(
            @Valid @RequestBody ReservationRequest request
    ) {
        ReservationCreateResponse response = reservationService.createReservation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(response));
    }

    @Operation(summary = "예약 수정", description = "예약 정보를 수정합니다. name, phone_num은 수정 불가합니다.")
    @PatchMapping("/{reservation_id}")
    public ResponseEntity<ApiResponse<Void>> updateReservation(
            @PathVariable("reservation_id") Long reservationId,
            @Valid @RequestBody ReservationUpdateRequest request
    ) {
        reservationService.updateReservation(reservationId, request);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

    @Operation(summary = "예약 삭제", description = "예약을 삭제합니다.")
    @DeleteMapping("/{reservation_id}")
    public ResponseEntity<ApiResponse<Void>> deleteReservation(
            @PathVariable("reservation_id") Long reservationId
    ) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
