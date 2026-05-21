package com.nailagent.backend.domain.payment.dto.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nailagent.backend.domain.reservation.entity.Reservation.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentUpdateRequest {

    @NotNull(message = "결제 상태는 필수입니다")
    @JsonProperty("payment_status")
    @Schema(description = "결제 상태", example = "PAID")
    private PaymentStatus paymentStatus;

    @NotBlank(message = "결제 키는 필수입니다")
    @JsonProperty("payment_key")
    @Schema(description = "토스 결제 고유 키", example = "tgen_20260521AbCdEf")
    private String paymentKey;

    @NotNull(message = "결제 금액은 필수입니다")
    @PositiveOrZero(message = "결제 금액은 0 이상이어야 합니다")
    @Schema(description = "결제 금액", example = "5000")
    private Integer amount;
}
