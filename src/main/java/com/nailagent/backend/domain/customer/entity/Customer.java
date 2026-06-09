package com.nailagent.backend.domain.customer.entity;

import com.nailagent.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "customers",
        uniqueConstraints = @UniqueConstraint(name = "uk_customers_name_phone", columnNames = {"name", "phone_num"})
)
@Getter
@NoArgsConstructor
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String kakaoUserId;

    @Column(unique = true)
    private String plusfriendUserKey;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNum;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer noshowCount;

    @Builder
    public Customer(String kakaoUserId, String plusfriendUserKey, String name, String phoneNum) {
        this.kakaoUserId = kakaoUserId;
        this.plusfriendUserKey = plusfriendUserKey;
        this.name = name;
        this.phoneNum = phoneNum;
        this.noshowCount = 0;
    }

    public void updateKakaoUserId(String kakaoUserId) {
        this.kakaoUserId = kakaoUserId;
    }

    public void updatePlusfriendUserKey(String plusfriendUserKey) {
        this.plusfriendUserKey = plusfriendUserKey;
    }
}
