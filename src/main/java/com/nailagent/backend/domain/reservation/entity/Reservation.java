package com.nailagent.backend.domain.reservation.entity;

import com.nailagent.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "reservation")
@Getter
@NoArgsConstructor
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private String name;
    private String phoneNum;
    private LocalDate reserveDate;
    private String reserveTime;
    private Integer estimatedDurationMin;
    private String service;
    private Boolean offRemoval;

    @Column(columnDefinition = "VARCHAR(255) DEFAULT '사장님'")
    private String designer;
    private Integer depositAmount;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'PENDING'")
    private VisitStatus visitStatus;

    @Builder
    public Reservation(Long customerId, String name, String phoneNum, LocalDate reserveDate,
                       String reserveTime, Integer estimatedDurationMin, String service,
                       Boolean offRemoval, String designer, Integer depositAmount) {
        this.customerId = customerId;
        this.name = name;
        this.phoneNum = phoneNum;
        this.reserveDate = reserveDate;
        this.reserveTime = reserveTime;
        this.estimatedDurationMin = estimatedDurationMin;
        this.service = service;
        this.offRemoval = offRemoval;
        this.designer = designer != null ? designer : "사장님";
        this.depositAmount = depositAmount;
        this.visitStatus = VisitStatus.PENDING;
    }

    public enum VisitStatus {
        PENDING, CONFIRMED, VISITED, NO_SHOW
    }
}
