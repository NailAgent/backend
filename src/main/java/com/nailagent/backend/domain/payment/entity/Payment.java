package com.nailagent.backend.domain.payment.entity;

import com.nailagent.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long reservationId;

    @Column(nullable = false, unique = true)
    private String paymentKey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'PENDING'")
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    private Integer paidAmount;

    @Builder
    public Payment(Long reservationId, String paymentKey, PaymentStatus paymentStatus, Integer paidAmount) {
        this.reservationId = reservationId;
        this.paymentKey = paymentKey;
        this.paymentStatus = paymentStatus;
        this.paidAmount = paidAmount;
    }

    public enum PaymentStatus {
        PENDING, PAID, FAILED, CANCELLED
    }
}
