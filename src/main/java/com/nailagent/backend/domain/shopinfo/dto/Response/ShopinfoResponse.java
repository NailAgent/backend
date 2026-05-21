package com.nailagent.backend.domain.shopinfo.dto.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShopinfoResponse {

    private String businessHour;
    private Integer closedDays;
    private String bookingFormText;
    private String servicesPrice;
    private String serviceDurations;
    private Integer depositAmount;
    private String accountNumber;
    private String policyText;
    private String bookingMessageText;

}
