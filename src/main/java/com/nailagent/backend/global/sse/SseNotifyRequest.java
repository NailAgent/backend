package com.nailagent.backend.global.sse;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SseNotifyRequest {

    @NotBlank(message = "고객명은 필수입니다")
    @JsonProperty("customer_name")
    @Schema(description = "고객명", example = "눈송이")
    private String customerName;

    @NotNull(message = "대기 여부는 필수입니다")
    @Schema(description = "고객 응답 대기 여부", example = "true")
    private Boolean waiting;
}
