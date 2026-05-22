package com.nailagent.backend.domain.reservation.dto.Response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class ReservationListResponse {

    private int currentPage;
    private int totalPages;
    private int size;
    private int currentSize;
    private long totalSize;
    private boolean hasPrevious;
    private boolean hasNext;
    private List<BookingItem> bookings;

    @Getter
    @Builder
    public static class BookingItem {
        private Long id;
        private String name;
        private String service;
        private LocalDate reserveDate;
        private String reserveTime;
        private Boolean offRemoval;
        private String designer;
        private String visitStatus;
        private String paymentStatus;
        private String imageUrl;
    }
}
