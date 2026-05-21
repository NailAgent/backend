package com.nailagent.backend.domain.reservation.dto.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nailagent.backend.domain.reservation.entity.Reservation.VisitStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ReservationUpdateRequest {

    @JsonProperty("reserve_date")
    @Schema(description = "예약 날짜", example = "2026-05-07")
    private LocalDate reserveDate;

    @Pattern(regexp = "^\\d{2}:\\d{2}-\\d{2}:\\d{2}$", message = "예약 시간 형식이 올바르지 않습니다 (예: 17:00-18:30)")
    @JsonProperty("reserve_time")
    @Schema(description = "예약 시간", example = "17:00-18:30")
    private String reserveTime;

    @PositiveOrZero(message = "소요 시간은 0 이상이어야 합니다")
    @JsonProperty("estimated_duration_min")
    @Schema(description = "총 소요 시간 (분)", example = "90")
    private Integer estimatedDurationMin;

    @Schema(description = "시술명", example = "젤네일")
    private String service;

    @JsonProperty("off_removal")
    @Schema(description = "젤제거 여부", example = "true")
    private Boolean offRemoval;

    @PositiveOrZero(message = "선입금 금액은 0 이상이어야 합니다")
    @JsonProperty("deposit_amount")
    @Schema(description = "선입금 금액", example = "5000")
    private Integer depositAmount;

    @Schema(description = "담당 디자이너", example = "사장님")
    private String designer;

    @JsonProperty("visit_status")
    @Schema(description = "방문 상태", example = "CONFIRMED")
    private VisitStatus visitStatus;
}
