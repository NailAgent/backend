package com.nailagent.backend.domain.customer.dto.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoCustomerRequest {

    @NotBlank(message = "카카오 유저 ID는 필수입니다")
    @JsonProperty("kakao_user_id")
    @Schema(description = "카카오 유저 고유 ID", example = "1867bbd...fc0")
    private String kakaoUserId;

    @JsonProperty("plusfriend_user_key")
    @Schema(description = "플러스친구 유저 키", example = "SPD8QKGzFvSE")
    private String plusfriendUserKey;

    @JsonProperty("is_friend")
    @Schema(description = "채널 추가 여부", example = "true")
    private Boolean isFriend;
}
