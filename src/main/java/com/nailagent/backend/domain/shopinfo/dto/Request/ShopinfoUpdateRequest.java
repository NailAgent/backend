package com.nailagent.backend.domain.shopinfo.dto.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShopinfoUpdateRequest {

    @JsonProperty("business_hour")
    @Schema(description = "영업 시간", example = "평일: 10:00-21:00\\n주말: 10:00-22:00")
    private String businessHour;

    @JsonProperty("closed_days")
    @Schema(description = "휴무 요일", example = "1")
    private Integer closedDays;

    @JsonProperty("booking_form_text")
    @Schema(description = "예약 양식 템플릿", example = "아래 예약 형식에 맞게 채워서 보내주세요.")
    private String bookingFormText;

    @JsonProperty("services_price")
    @Schema(description = "대표 시술 가격표", example = "{\\\"젤네일\\\": 50000, \\\"기본네일\\\": 30000}")
    private String servicesPrice;

    @Min(value = 0, message = "예약금은 0 이상이어야 합니다")
    @JsonProperty("deposit_amount")
    @Schema(description = "예약금", example = "5000")
    private Integer depositAmount;

    @JsonProperty("account_number")
    @Schema(description = "계좌번호", example = "우리은행 1002-061-241977")
    private String accountNumber;

    @JsonProperty("policy_text")
    @Schema(description = "정책 안내", example = "영업시간: 10:00-22:00 / 매주 월요일 정기휴무")
    private String policyText;

    @JsonProperty("booking_message_text")
    @Schema(description = "예약 확정 멘트", example = "예약이 확정되었습니다!")
    private String bookingMessageText;
}