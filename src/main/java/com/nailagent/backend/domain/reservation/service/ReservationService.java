package com.nailagent.backend.domain.reservation.service;

import com.nailagent.backend.domain.reservation.dto.ScheduleResponse;
import com.nailagent.backend.domain.reservation.entity.Reservation;
import com.nailagent.backend.domain.reservation.repository.ReservationRepository;
import com.nailagent.backend.domain.shopinfo.entity.Shopinfo;
import com.nailagent.backend.domain.shopinfo.repository.ShopinfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ShopinfoRepository shopinfoRepository;

    public ScheduleResponse getSchedule(LocalDate date) {

        // 샵 설정 조회 (항상 id=1 단일 row)
        Shopinfo shopinfo = shopinfoRepository.findById(1).orElseThrow();

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
}
