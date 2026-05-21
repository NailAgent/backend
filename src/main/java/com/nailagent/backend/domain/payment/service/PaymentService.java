package com.nailagent.backend.domain.payment.service;

import com.nailagent.backend.domain.payment.dto.Request.PaymentUpdateRequest;
import com.nailagent.backend.domain.reservation.entity.Reservation;
import com.nailagent.backend.domain.reservation.repository.ReservationRepository;
import com.nailagent.backend.global.exception.CustomException;
import com.nailagent.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ReservationRepository reservationRepository;

    @Transactional
    public void updatePayment(Long reservationId, PaymentUpdateRequest request) {
        // 멱등성 처리 — 동일 payment_key 중복 수신 방지
        if (reservationRepository.existsByPaymentKey(request.getPaymentKey())) {
            throw new CustomException(ErrorCode.DUPLICATE_PAYMENT_KEY);
        }

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        reservation.updatePayment(request.getPaymentKey(), request.getPaymentStatus(), request.getAmount());
    }
}
