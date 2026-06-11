package com.nailagent.backend.domain.reservation.repository;

import com.nailagent.backend.domain.payment.entity.Payment.PaymentStatus;
import com.nailagent.backend.domain.reservation.entity.Reservation;
import com.nailagent.backend.domain.reservation.entity.Reservation.VisitStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByReserveDate(LocalDate reserveDate);

    List<Reservation> findAllByReserveDateAndVisitStatusNot(LocalDate reserveDate, VisitStatus visitStatus);

    Optional<Reservation> findTopByCustomerIdOrderByIdDesc(Long customerId);

    @Query("SELECT r FROM Reservation r WHERE r.depositAmount > 0 AND r.paymentStatus = :paymentStatus AND r.visitStatus = :visitStatus AND r.createdAt < :cutoff")
    List<Reservation> findExpiredUnpaidReservations(
            @Param("paymentStatus") PaymentStatus paymentStatus,
            @Param("visitStatus") VisitStatus visitStatus,
            @Param("cutoff") LocalDateTime cutoff
    );
}
