package com.nailagent.backend.domain.customer.entity;

import com.nailagent.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Getter
@NoArgsConstructor
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String kakaoUserId;
    private String name;

    @Column(nullable = false)
    private String phoneNum;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer noshowCount;

    @Builder
    public Customer(String kakaoUserId, String name, String phoneNum) {
        this.kakaoUserId = kakaoUserId;
        this.name = name;
        this.phoneNum = phoneNum;
        this.noshowCount = 0;
    }
}
