package com.nailagent.backend.domain.customer.dto.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoCustomerResponse {

    @JsonProperty("is_existing")
    @Schema(description = "기존 고객 여부", example = "true")
    private Boolean isExisting;

    @JsonProperty("name")
    @Schema(description = "고객 성함 (신규 고객이면 null)", example = "홍다인")
    private String name;

    @JsonProperty("phone_num")
    @Schema(description = "전화번호 (신규 고객이면 null)", example = "010-9999-3333")
    private String phoneNum;
}
