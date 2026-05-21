package com.nailagent.backend.domain.shopinfo.entity;

import com.nailagent.backend.domain.shopinfo.dto.Request.ShopinfoUpdateRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "shopinfo")
@Getter
@NoArgsConstructor
public class Shopinfo {

    @Id
    private Integer id;

    private String businessHour;
    private Integer closedDays;
    private String bookingFormText;
    private String servicesPrice;
    private String serviceDurations;
    private Integer depositAmount;
    private String accountNumber;
    private String policyText;
    private String bookingMessageText;
    private LocalDateTime updatedAt;

    public void update(ShopinfoUpdateRequest request) {

        if (request.getBusinessHour() != null) this.businessHour = request.getBusinessHour();
        if (request.getClosedDays() != null) this.closedDays = request.getClosedDays();
        if (request.getBookingFormText() != null) this.bookingFormText = request.getBookingFormText();
        if (request.getServicesPrice() != null) this.servicesPrice = request.getServicesPrice();
        if (request.getServiceDurations() != null) this.serviceDurations = request.getServiceDurations();
        if (request.getDepositAmount() != null) this.depositAmount = request.getDepositAmount();
        if (request.getAccountNumber() != null) this.accountNumber = request.getAccountNumber();
        if (request.getPolicyText() != null) this.policyText = request.getPolicyText();
        if (request.getBookingMessageText() != null) this.bookingMessageText = request.getBookingMessageText();
        this.updatedAt = LocalDateTime.now();
    }

}
