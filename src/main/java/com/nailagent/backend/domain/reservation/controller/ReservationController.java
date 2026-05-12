package com.nailagent.backend.domain.reservation.controller;

import com.nailagent.backend.domain.reservation.dto.ScheduleResponse;
import com.nailagent.backend.domain.reservation.service.ReservationService;
import com.nailagent.backend.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/schedule")
    public ApiResponse<ScheduleResponse> getSchedule(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ApiResponse.ok(reservationService.getSchedule(date));
    }
}
