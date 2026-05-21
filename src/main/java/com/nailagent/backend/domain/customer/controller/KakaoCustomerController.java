package com.nailagent.backend.domain.customer.controller;

import com.nailagent.backend.domain.customer.dto.Request.KakaoCustomerRequest;
import com.nailagent.backend.domain.customer.dto.Response.KakaoCustomerResponse;
import com.nailagent.backend.domain.customer.service.CustomerService;
import com.nailagent.backend.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "KakaoCustomer", description = "카카오 고객 연동 API (n8n 전용)")
@RestController
@RequestMapping("/api/v1/kakao-customers")
@RequiredArgsConstructor
public class KakaoCustomerController {

    private final CustomerService customerService;

    @Operation(
            summary = "카카오 고객 조회",
            description = "카카오 유저 ID로 기존/신규 고객 여부를 조회합니다. 기존 고객이면 이름과 전화번호를 반환합니다."
    )
    @PostMapping
    public ResponseEntity<ApiResponse<KakaoCustomerResponse>> lookupKakaoCustomer(
            @RequestBody @Valid KakaoCustomerRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok(customerService.lookupKakaoCustomer(request)));
    }
}
