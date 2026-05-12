package com.nailagent.backend.domain.shopinfo.entity;

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
    private String servicesJson;
    private Integer depositAmount;
    private String accountNumber;
    private String policyText;
    private String bookingMessageText;
    private LocalDateTime updatedAt;

}
