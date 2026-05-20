package com.nailagent.backend.domain.customer.dto.Response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class CustomerListResponse {

    private int currentPage;
    private int totalPages;
    private int size;
    private int currentSize;
    private long totalSize;
    private boolean hasPrevious;
    private boolean hasNext;
    private List<CustomerItem> customers;

    @Getter
    @Builder
    public static class CustomerItem {
        private Long id;
        private String name;
        private String phoneNum;
        private Integer noshowCount;
        private LocalDate lastReserveDate;
    }

}
