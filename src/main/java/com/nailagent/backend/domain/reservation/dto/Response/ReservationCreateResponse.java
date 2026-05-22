package com.nailagent.backend.domain.reservation.dto.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationCreateResponse {

    private Long id;

    @JsonProperty("calendar_sync")
    private boolean calendarSync;
}
