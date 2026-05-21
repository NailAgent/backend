package com.nailagent.backend.domain.customer.controller;

import com.nailagent.backend.domain.customer.dto.Response.CustomerListResponse;
import com.nailagent.backend.domain.customer.dto.Response.CustomerResponse;
import com.nailagent.backend.domain.customer.service.CustomerService;
import com.nailagent.backend.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Customer", description = "고객 관련 API")
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "고객 ID별 상세 조회", description = "고객 ID를 기반으로 고객 정보를 조회합니다.")
    @GetMapping("/{customer_id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomer(
            @PathVariable("customer_id") Long customerId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(customerService.getCustomer(customerId)));
    }

    @Operation(summary = "전체 고객 목록 조회", description = "페이지네이션을 통해 전체 고객 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<CustomerListResponse>> getCustomers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "100") int size
    ) {
        return ResponseEntity.ok(ApiResponse.ok(customerService.getCustomers(page, size)));
    }

}
