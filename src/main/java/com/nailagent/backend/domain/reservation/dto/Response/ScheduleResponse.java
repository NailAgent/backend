package com.nailagent.backend.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ScheduleResponse {

    private String date;
    private String businessHour;
    private List<BookedSchedule> bookedSchedules;

    @Getter
    @Builder
    public static class BookedSchedule {
        private String reserveTime;
        private Integer durationMin;
    }
}
