package com.nailagent.backend.domain.reservation.repository;

import com.nailagent.backend.domain.reservation.entity.ReservationImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationImageRepository extends JpaRepository<ReservationImage, Long> {

    List<ReservationImage> findAllByReservationIdIn(List<Long> reservationIds);

    List<ReservationImage> findAllByReservationId(Long reservationId);
}
