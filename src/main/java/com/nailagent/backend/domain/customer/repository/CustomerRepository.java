package com.nailagent.backend.domain.customer.repository;

import com.nailagent.backend.domain.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // 이름 + 전화번호로 기존 고객 조회 (예약 생성 시 신규/기존 고객 판별)
    Optional<Customer> findByNameAndPhoneNum(String name, String phoneNum);

    // 카카오 유저 ID로 기존 고객 조회
    Optional<Customer> findByKakaoUserId(String kakaoUserId);

    // 고객 ID로 가장 최근 예약 날짜 조회
    @Query("SELECT MAX(r.reserveDate) FROM Reservation r WHERE r.customerId = :customerId")
    Optional<LocalDate> findLastReserveDateByCustomerId(@Param("customerId") Long customerId);

    // 고객 ID 목록 기준으로 최근 예약일 일괄 조회 (N+1 방지)
    @Query("SELECT r.customerId, MAX(r.reserveDate) FROM Reservation r WHERE r.customerId IN :customerIds GROUP BY r.customerId")
    List<Object[]> findLastReserveDatesByCustomerIds(@Param("customerIds") List<Long> customerIds);

}
