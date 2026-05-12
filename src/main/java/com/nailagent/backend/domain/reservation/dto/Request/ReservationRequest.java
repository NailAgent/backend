package com.nailagent.backend.domain.reservation.dto.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ReservationRequest {

    @NotBlank(message = "예약자 성함은 필수입니다")
    @Schema(description = "예약자 성함", example = "눈송이")
    private String name;

    @NotBlank(message = "전화번호는 필수입니다")
    @JsonProperty("phone_num")
    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phoneNum;

    @NotNull(message = "예약 날짜는 필수입니다")
    @JsonProperty("reserve_date")
    @Schema(description = "예약 날짜", example = "2026-05-07")
    private LocalDate reserveDate;

    @JsonProperty("reserve_time")
    @Schema(description = "예약 시간", example = "17:00-18:30")
    private String reserveTime;

    @JsonProperty("estimated_duration_min")
    @Schema(description = "총 소요 시간 (분)", example = "90")
    private Integer estimatedDurationMin;

    @Schema(description = "시술명", example = "젤네일")
    private String service;

    @JsonProperty("off_removal")
    @Schema(description = "젤제거 여부", example = "true")
    private Boolean offRemoval;

    @JsonProperty("deposit_amount")
    @Schema(description = "선입금 금액", example = "5000")
    private Integer depositAmount;

    @Schema(description = "담당 디자이너 (기본값: 사장님)", example = "null")
    private String designer;
}