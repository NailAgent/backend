package com.nailagent.backend.domain.reservation.dto.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ReservationRequest {

    @JsonProperty("kakao_user_id")
    @Schema(description = "카카오 유저 ID", example = "kakao_001")
    private String kakaoUserId;

    @JsonProperty("plusfriend_user_key")
    @Schema(description = "카카오 플러스친구 유저 키", example = "SPD8QKGzFvSE")
    private String plusfriendUserKey;

    @NotBlank(message = "예약자 성함은 필수입니다")
    @Schema(description = "예약자 성함", example = "눈송이")
    private String name;

    @NotBlank(message = "전화번호는 필수입니다")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다 (예: 010-1234-5678)")
    @JsonProperty("phone_num")
    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phoneNum;

    @NotNull(message = "예약 날짜는 필수입니다")
    @JsonProperty("reserve_date")
    @Schema(description = "예약 날짜", example = "2026-05-07")
    private LocalDate reserveDate;

    @NotBlank(message = "예약 시간은 필수입니다")
    @Pattern(regexp = "^\\d{2}:\\d{2}-\\d{2}:\\d{2}$", message = "예약 시간 형식이 올바르지 않습니다 (예: 17:00-18:30)")
    @JsonProperty("reserve_time")
    @Schema(description = "예약 시간", example = "17:00-18:30")
    private String reserveTime;

    @NotNull(message = "소요 시간은 필수입니다")
    @PositiveOrZero(message = "소요 시간은 0 이상이어야 합니다")
    @JsonProperty("estimated_duration_min")
    @Schema(description = "총 소요 시간 (분)", example = "90")
    private Integer estimatedDurationMin;

    @NotBlank(message = "시술명은 필수입니다")
    @Schema(description = "시술명", example = "젤네일")
    private String service;

    @NotNull(message = "젤제거 여부는 필수입니다")
    @JsonProperty("off_removal")
    @Schema(description = "젤제거 여부", example = "true")
    private Boolean offRemoval;

    @NotNull(message = "선입금 금액은 필수입니다")
    @PositiveOrZero(message = "선입금 금액은 0 이상이어야 합니다")
    @JsonProperty("deposit_amount")
    @Schema(description = "선입금 금액", example = "5000")
    private Integer depositAmount;

    @Schema(description = "담당 디자이너 (기본값: 사장님)", example = "null")
    private String designer;
}
