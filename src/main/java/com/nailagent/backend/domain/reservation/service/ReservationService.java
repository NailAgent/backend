package com.nailagent.backend.domain.reservation.service;

import com.nailagent.backend.domain.customer.entity.Customer;
import com.nailagent.backend.domain.customer.repository.CustomerRepository;
import com.nailagent.backend.domain.reservation.dto.Request.ReservationRequest;
import com.nailagent.backend.domain.reservation.dto.Response.ReservationListResponse;
import com.nailagent.backend.domain.reservation.dto.Response.ScheduleResponse;
import com.nailagent.backend.domain.reservation.entity.Reservation;
import com.nailagent.backend.domain.reservation.repository.ReservationRepository;
import com.nailagent.backend.domain.shopinfo.entity.Shopinfo;
import com.nailagent.backend.domain.shopinfo.repository.ShopinfoRepository;
import com.nailagent.backend.global.exception.CustomException;
import com.nailagent.backend.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ShopinfoRepository shopinfoRepository;
    private final CustomerRepository customerRepository;

    public ScheduleResponse getSchedule(LocalDate date) {

        // 샵 설정 조회 (항상 id=1 단일 row)
        Shopinfo shopinfo = shopinfoRepository.findById(1)
                .orElseThrow(() -> new CustomException(ErrorCode.SHOPINFO_NOT_FOUND));

        // 해당 날짜의 예약 목록 조회
        List<Reservation> reservations = reservationRepository.findAllByReserveDate(date);

        // 예약 목록을 DTO로 변환
        List<ScheduleResponse.BookedSchedule> bookedSchedules = reservations.stream()
                .map(r -> ScheduleResponse.BookedSchedule.builder()
                        .reserveTime(r.getReserveTime())
                        .durationMin(r.getEstimatedDurationMin())
                        .build())
                .toList();

        // 최종 응답 반환
        return ScheduleResponse.builder()
                .date(date.toString())
                .businessHour(shopinfo.getBusinessHour())
                .bookedSchedules(bookedSchedules)
                .build();
    }

    public ReservationListResponse getReservations(int page, int size) {

        // 페이지 값 검증
        if (page < 1 || size < 1) {
            throw new CustomException(ErrorCode.INVALID_PAGINATION);
        }

        // 페이지네이션 조회
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);

        // 예약 목록을 DTO로 변환
        List<ReservationListResponse.BookingItem> bookings = reservationPage.getContent().stream()
                .map(r -> ReservationListResponse.BookingItem.builder()
                        .id(r.getId())
                        .name(r.getName())
                        .service(r.getService())
                        .reserveDate(r.getReserveDate())
                        .reserveTime(r.getReserveTime())
                        .offRemoval(r.getOffRemoval())
                        .designer(r.getDesigner())
                        .visitStatus(r.getVisitStatus().name())
                        .build())
                .toList();

        // 최종 응답 반환
        return ReservationListResponse.builder()
                .currentPage(page)
                .totalPages(reservationPage.getTotalPages())
                .size(size)
                .currentSize(reservationPage.getNumberOfElements())
                .totalSize(reservationPage.getTotalElements())
                .hasPrevious(reservationPage.hasPrevious())
                .hasNext(reservationPage.hasNext())
                .bookings(bookings)
                .build();
    }

    public void createReservation(ReservationRequest request) {

        // name + phone_num으로 기존 고객 조회, 없으면 신규 등록
        Customer customer = customerRepository
                .findByNameAndPhoneNum(request.getName(), request.getPhoneNum())
                .orElseGet(() -> {
                    try {
                        return customerRepository.save(
                                Customer.builder()
                                        .name(request.getName())
                                        .phoneNum(request.getPhoneNum())
                                        .build()
                        );
                    } catch (DataIntegrityViolationException e) {
                        // 동시 요청으로 unique 제약 위반 시 기존 고객 재조회
                        return customerRepository
                                .findByNameAndPhoneNum(request.getName(), request.getPhoneNum())
                                .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));
                    }
                });

        // 예약 생성
        Reservation reservation = Reservation.builder()
                .customerId(customer.getId())
                .name(request.getName())
                .phoneNum(request.getPhoneNum())
                .reserveDate(request.getReserveDate())
                .reserveTime(request.getReserveTime())
                .estimatedDurationMin(request.getEstimatedDurationMin())
                .service(request.getService())
                .offRemoval(request.getOffRemoval())
                .designer(
                        request.getDesigner() == null || request.getDesigner().isBlank()
                                ? "사장님"
                                : request.getDesigner()
                )
                .depositAmount(request.getDepositAmount())
                .build();

        reservationRepository.save(reservation);
    }
}
