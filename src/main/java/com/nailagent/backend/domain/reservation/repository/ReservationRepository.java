package com.nailagent.backend.domain.reservation.repository;

import com.nailagent.backend.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByReserveDate(LocalDate reserveDate);
}
