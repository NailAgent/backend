package com.nailagent.backend.domain.payment.service;

import com.nailagent.backend.domain.payment.dto.Request.PaymentUpdateRequest;
import com.nailagent.backend.domain.payment.entity.Payment.PaymentStatus;
import com.nailagent.backend.domain.reservation.entity.Reservation;
import com.nailagent.backend.domain.reservation.repository.ReservationRepository;
import com.nailagent.backend.global.exception.CustomException;
import com.nailagent.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ReservationRepository reservationRepository;

    @Transactional
    public void updatePayment(Long reservationId, PaymentUpdateRequest request) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        try {
            reservation.updatePayment(request.getPaymentKey(), request.getPaymentStatus(), request.getAmount());
            reservationRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(ErrorCode.DUPLICATE_PAYMENT_KEY);
        }
    }

    @Transactional
    public void refundPayment(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        if (reservation.getPaymentStatus() != PaymentStatus.PAID) {
            throw new CustomException(ErrorCode.PAYMENT_NOT_PAID);
        }

        reservation.cancelPayment();
    }
}
