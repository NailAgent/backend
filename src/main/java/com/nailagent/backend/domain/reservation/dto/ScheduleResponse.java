package com.nailagent.backend.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ScheduleResponse {

    private String date;

    @JsonProperty("business_hour")
    private String businessHour;

    @JsonProperty("booked_schedules")
    private List<BookedSchedule> bookedSchedules;

    @Getter
    @Builder
    public static class BookedSchedule {
        @JsonProperty("reserve_time")
        private String reserveTime;

        @JsonProperty("duration_min")
        private Integer durationMin;
    }
}
