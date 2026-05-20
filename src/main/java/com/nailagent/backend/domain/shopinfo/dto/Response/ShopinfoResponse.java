package com.nailagent.backend.domain.shopinfo.dto.Response;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShopinfoResponse {

    private String businessHour;
    private Integer closedDays;
    private String bookingFormText;
    @JsonRawValue
    private String servicesPrice;
    private Integer depositAmount;
    private String accountNumber;
    private String policyText;
    private String bookingMessageText;

}
