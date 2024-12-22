package kr.co.store.api.store.order.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class ErrorResponseDto {

    @Builder
    public ErrorResponseDto(String message,String redirectUrl) {
        this.message = message;
        this.redirectUrl = redirectUrl;
    }

    private String message;
    private String redirectUrl;


}
