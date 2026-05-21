package com.nailagent.backend.domain.shopinfo.service;

import com.nailagent.backend.domain.shopinfo.dto.Request.ShopinfoUpdateRequest;
import com.nailagent.backend.domain.shopinfo.dto.Response.ShopinfoResponse;
import com.nailagent.backend.domain.shopinfo.entity.Shopinfo;
import com.nailagent.backend.domain.shopinfo.repository.ShopinfoRepository;
import com.nailagent.backend.global.exception.CustomException;
import com.nailagent.backend.global.exception.ErrorCode;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopinfoService {

    private final ShopinfoRepository shopinfoRepository;

    public ShopinfoResponse getShopinfo() {

        Shopinfo shopinfo = shopinfoRepository.findById(1)
                .orElseThrow(() -> new CustomException(ErrorCode.SHOPINFO_NOT_FOUND));

        return ShopinfoResponse.builder()
                .businessHour(shopinfo.getBusinessHour())
                .closedDays(shopinfo.getClosedDays())
                .bookingFormText(shopinfo.getBookingFormText())
                .servicesPrice(shopinfo.getServicesPrice())
                .serviceDurations(shopinfo.getServiceDurations())
                .depositAmount(shopinfo.getDepositAmount())
                .accountNumber(shopinfo.getAccountNumber())
                .policyText((shopinfo.getPolicyText()))
                .bookingMessageText(shopinfo.getBookingMessageText())
                .build();
    }

    @Transactional
    public void updateShopinfo(ShopinfoUpdateRequest request) {

        Shopinfo shopinfo = shopinfoRepository.findById(1)
                .orElseThrow(() -> new CustomException(ErrorCode.SHOPINFO_NOT_FOUND));

        shopinfo.update(request);
    }

}
