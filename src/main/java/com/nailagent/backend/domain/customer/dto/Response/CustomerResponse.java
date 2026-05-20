package com.nailagent.backend.domain.customer.dto.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerResponse {

    private Long id;
    private String kakaoUserId;
    private String name;
    private String phoneNum;
    private Integer noshowCount;

}
