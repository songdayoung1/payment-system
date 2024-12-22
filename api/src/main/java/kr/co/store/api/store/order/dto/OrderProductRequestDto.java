package kr.co.store.api.store.order.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderProductRequestDto {

    @NotNull(message = "orderProductId is not null")
    private Long orderProductId;

    @NotNull(message = "quantity is not null")
    private int quantity;

    @NotNull(message = "paymentPrice is not null")
    private int paymentPrice;
}
