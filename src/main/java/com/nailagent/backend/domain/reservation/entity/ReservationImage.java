package com.nailagent.backend.domain.reservation.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservation_image")
@Getter
@NoArgsConstructor
public class ReservationImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reservationId;
    private String imageUrl;

    @Builder
    public ReservationImage(Long reservationId, String imageUrl) {
        this.reservationId = reservationId;
        this.imageUrl = imageUrl;
    }
}
