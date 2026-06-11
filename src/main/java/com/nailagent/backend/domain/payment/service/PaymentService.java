package com.nailagent.backend.domain.payment.service;

import com.nailagent.backend.domain.payment.dto.Request.PaymentUpdateRequest;
import com.nailagent.backend.domain.payment.entity.Payment.PaymentStatus;
import com.nailagent.backend.domain.reservation.entity.Reservation;
import com.nailagent.backend.domain.reservation.repository.ReservationRepository;
import com.nailagent.backend.global.exception.CustomException;
import com.nailagent.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ReservationRepository reservationRepository;
    private final RestClient restClient = RestClient.create();

    @Value("${toss.secret-key}")
    private String tossSecretKey;

    @Transactional
    public void confirmPayment(String paymentKey, String orderId, Integer amount) {
        String encodedKey = Base64.getEncoder()
                .encodeToString((tossSecretKey + ":").getBytes(StandardCharsets.UTF_8));

        try {
            restClient.post()
                    .uri("https://api.tosspayments.com/v1/payments/confirm")
                    .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("paymentKey", paymentKey, "orderId", orderId, "amount", amount))
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientException e) {
            throw new CustomException(ErrorCode.TOSS_CONFIRM_FAILED);
        }

        String[] parts = orderId.split("_");
        if (parts.length < 2) {
            throw new CustomException(ErrorCode.RESERVATION_NOT_FOUND);
        }
        Long reservationId = Long.parseLong(parts[1]);
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        try {
            reservation.updatePayment(paymentKey, PaymentStatus.PAID, amount);
            reservationRepository.flush();
        } catch (DataIntegrityViolationException e) {
            String message = e.getMostSpecificCause().getMessage();
            if (message != null && message.contains("payment_key")) {
                throw new CustomException(ErrorCode.DUPLICATE_PAYMENT_KEY);
            }
            throw e;
        }
    }

    @Transactional
    public void updatePayment(Long reservationId, PaymentUpdateRequest request) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        try {
            reservation.updatePayment(request.getPaymentKey(), request.getPaymentStatus(), request.getAmount());
            reservationRepository.flush();
        } catch (DataIntegrityViolationException e) {
            String message = e.getMostSpecificCause().getMessage();
            if (message != null && message.contains("payment_key")) {
                throw new CustomException(ErrorCode.DUPLICATE_PAYMENT_KEY);
            }
            throw e;
        }
    }

    @Transactional
    public void refundPayment(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        if (reservation.getPaymentStatus() != PaymentStatus.PAID) {
            throw new CustomException(ErrorCode.PAYMENT_NOT_PAID);
        }

        String encodedKey = Base64.getEncoder()
                .encodeToString((tossSecretKey + ":").getBytes(StandardCharsets.UTF_8));

        try {
            restClient.post()
                    .uri("https://api.tosspayments.com/v1/payments/{paymentKey}/cancel",
                            reservation.getPaymentKey())
                    .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("cancelReason", "고객 요청으로 인한 취소"))
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientException e) {
            throw new CustomException(ErrorCode.TOSS_CANCEL_FAILED);
        }

        reservation.cancelPayment();
    }
}
