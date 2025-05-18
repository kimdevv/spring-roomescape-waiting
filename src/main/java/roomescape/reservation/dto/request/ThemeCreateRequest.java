package roomescape.reservation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ThemeCreateRequest(@NotBlank(message = "이름이 입력되지 않았습니다.") String name,
                                 @NotBlank(message = "테마 설명이 입력되지 않았습니다.") String description,
                                 @NotBlank(message = "테마 썸네일 URL이 입력되지 않았습니다.") String thumbnail) {

}
