package com.nailagent.backend.domain.reservation.entity;

import com.nailagent.backend.global.common.BaseEntity;
import jakarta.persistence.*;
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
    private String designer;
    private Integer depositAmount;

    @Enumerated(EnumType.STRING)
    private VisitStatus visitStatus;

    public enum VisitStatus {
        PENDING, CONFIRMED, VISITED, NO_SHOW
    }
}
