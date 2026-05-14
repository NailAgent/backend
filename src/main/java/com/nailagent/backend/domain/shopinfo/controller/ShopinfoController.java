package com.nailagent.backend.domain.shopinfo.controller;

import com.nailagent.backend.domain.shopinfo.dto.Request.ShopinfoUpdateRequest;
import com.nailagent.backend.domain.shopinfo.dto.Response.ShopinfoResponse;
import com.nailagent.backend.domain.shopinfo.service.ShopinfoService;
import com.nailagent.backend.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Shopinfo", description = "샵 정보 관련 API")
@RestController
@RequestMapping("/api/v1/shopinfo")
@RequiredArgsConstructor
public class ShopinfoController {

    private final ShopinfoService shopinfoService;

    @Operation(summary = "샵 정보 조회", description = "샵 정보를 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<ShopinfoResponse>> getShopinfo() {
        return ResponseEntity.ok(ApiResponse.ok(shopinfoService.getShopinfo()));
    }

    @Operation(summary = "샵 정보 수정", description = "샵 정보를 수정합니다.")
    @PatchMapping
    public ResponseEntity<ApiResponse<Void>> updateShopinfo(
            @Valid @RequestBody ShopinfoUpdateRequest request
    ) {
        shopinfoService.updateShopinfo(request);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }

}
