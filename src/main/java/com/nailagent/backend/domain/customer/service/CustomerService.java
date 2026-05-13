package com.nailagent.backend.domain.customer.service;

import com.nailagent.backend.domain.customer.dto.Response.CustomerListResponse;
import com.nailagent.backend.domain.customer.dto.Response.CustomerResponse;
import com.nailagent.backend.domain.customer.entity.Customer;
import com.nailagent.backend.domain.customer.repository.CustomerRepository;
import com.nailagent.backend.global.exception.CustomException;
import com.nailagent.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponse getCustomer(Long customerId) {

        // 고객 조회
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.CUSTOMER_NOT_FOUND));

        return CustomerResponse.builder()
                .id(customer.getId())
                .kakaoUserId(customer.getKakaoUserId())
                .name(customer.getName())
                .phoneNum(customer.getPhoneNum())
                .noshowCount(customer.getNoshowCount())
                .build();
    }

    public CustomerListResponse getCustomers(int page, int size) {

        // 페이지 값 검증
        if (page < 1 || size < 1) {
            throw new CustomException(ErrorCode.INVALID_PAGINATION);
        }

        // 페이지네이션 조회
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Customer> customerPage = customerRepository.findAll(pageable);

        // 고객 목록을 DTO로 변환
        List<CustomerListResponse.CustomerItem> customers = customerPage.getContent().stream()
                .map(c -> CustomerListResponse.CustomerItem.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .phoneNum(c.getPhoneNum())
                        .noshowCount(c.getNoshowCount())
                        .lastReserveDate(customerRepository.findLastReserveDateByCustomerId(c.getId()).orElse(null))
                        .build())
                .toList();

        // 최종 응답 반환
        return CustomerListResponse.builder()
                .currentPage(page)
                .totalPages(customerPage.getTotalPages())
                .size(size)
                .currentSize(customerPage.getNumberOfElements())
                .totalSize(customerPage.getTotalElements())
                .hasPrevious(customerPage.hasPrevious())
                .hasNext(customerPage.hasNext())
                .customers(customers)
                .build();
    }
}
