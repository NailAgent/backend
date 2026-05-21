package com.nailagent.backend.domain.customer.service;

import com.nailagent.backend.domain.customer.dto.Request.KakaoCustomerRequest;
import com.nailagent.backend.domain.customer.dto.Response.CustomerListResponse;
import com.nailagent.backend.domain.customer.dto.Response.CustomerResponse;
import com.nailagent.backend.domain.customer.dto.Response.KakaoCustomerResponse;
import com.nailagent.backend.domain.customer.entity.Customer;
import com.nailagent.backend.domain.customer.repository.CustomerRepository;
import com.nailagent.backend.global.exception.CustomException;
import com.nailagent.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public KakaoCustomerResponse lookupKakaoCustomer(KakaoCustomerRequest request) {
        if (request.getPlusfriendUserKey() == null) {
            return KakaoCustomerResponse.builder()
                    .isExisting(false)
                    .name(null)
                    .phoneNum(null)
                    .build();
        }
        return customerRepository.findByPlusfriendUserKey(request.getPlusfriendUserKey())
                .map(customer -> KakaoCustomerResponse.builder()
                        .isExisting(true)
                        .kakaoUserId(request.getKakaoUserId())
                        .plusfriendUserKey(request.getPlusfriendUserKey())
                        .name(customer.getName())
                        .phoneNum(customer.getPhoneNum())
                        .build())
                .orElse(KakaoCustomerResponse.builder()
                        .isExisting(false)
                        .kakaoUserId(request.getKakaoUserId())
                        .plusfriendUserKey(request.getPlusfriendUserKey())
                        .name(null)
                        .phoneNum(null)
                        .build());
    }

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

        // 고객 ID 목록으로 최근 예약일 일괄 조회 (N+1 방지)
        List<Long> customerIds = customerPage.getContent().stream()
                .map(Customer::getId)
                .toList();

        Map<Long, LocalDate> lastReserveDateMap = customerRepository.findLastReserveDatesByCustomerIds(customerIds)
                .stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],
                        row -> (LocalDate) row[1]
                ));

        // 고객 목록을 DTO로 변환
        List<CustomerListResponse.CustomerItem> customers = customerPage.getContent().stream()
                .map(c -> CustomerListResponse.CustomerItem.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .phoneNum(c.getPhoneNum())
                        .noshowCount(c.getNoshowCount())
                        .lastReserveDate(lastReserveDateMap.get(c.getId()))
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
