package com.nailagent.backend.domain.payment.dto.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentConfirmRequest {

    @NotBlank(message = "결제 키는 필수입니다")
    @JsonProperty("payment_key")
    @Schema(description = "토스 결제 고유 키", example = "tgen_20260521AbCdEf")
    private String paymentKey;

    @NotBlank(message = "주문 ID는 필수입니다")
    @JsonProperty("order_id")
    @Schema(description = "주문 ID (예약 ID)", example = "42")
    private String orderId;

    @NotNull(message = "결제 금액은 필수입니다")
    @Positive(message = "결제 금액은 0보다 커야 합니다")
    @Schema(description = "결제 금액", example = "5000")
    private Integer amount;
}
