package com.nailagent.backend.domain.payment.controller;

import com.nailagent.backend.domain.payment.dto.Request.PaymentConfirmRequest;
import com.nailagent.backend.domain.payment.dto.Request.PaymentUpdateRequest;
import com.nailagent.backend.domain.payment.service.PaymentService;
import com.nailagent.backend.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Payment", description = "결제 관련 API")
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "결제 최종 승인", description = "토스페이먼츠 결제 승인 API를 호출하고 DB에 PAID 상태를 저장합니다.")
    @PostMapping("/confirm")
    public ResponseEntity<ApiResponse<Void>> confirmPayment(
            @Valid @RequestBody PaymentConfirmRequest request
    ) {
        paymentService.confirmPayment(request.getPaymentKey(), request.getOrderId(), request.getAmount());
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

    @Operation(summary = "결제 상태 업데이트", description = "토스페이먼츠 결제 완료 후 결제 상태를 업데이트합니다. 동일한 payment_key가 이미 존재하면 409 CONFLICT를 반환합니다.")
    @PatchMapping("/{reservation_id}")
    public ResponseEntity<ApiResponse<Void>> updatePayment(
            @PathVariable("reservation_id") Long reservationId,
            @Valid @RequestBody PaymentUpdateRequest request
    ) {
        paymentService.updatePayment(reservationId, request);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

    @Operation(summary = "결제 환불", description = "결제 완료(PAID) 상태의 예약을 환불(CANCELLED) 처리합니다. PAID 상태가 아니면 400을 반환합니다.")
    @PostMapping("/{reservation_id}/refund")
    public ResponseEntity<ApiResponse<Void>> refundPayment(
            @PathVariable("reservation_id") Long reservationId
    ) {
        paymentService.refundPayment(reservationId);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
