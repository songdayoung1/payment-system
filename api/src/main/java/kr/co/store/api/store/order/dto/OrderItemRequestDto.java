package kr.co.store.api.store.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItemRequestDto {
    private Long itemId;
    private Integer paymentPrice;
    private Integer quantity;

    @Builder
    public OrderItemRequestDto(Long itemId, Integer paymentPrice, Integer quantity) {
        this.itemId = itemId;
        this.paymentPrice = paymentPrice;
        this.quantity = quantity;
    }

//    public static OrderItem toOrderItem(OrderItemRequestDto orderItemRequestDto) {
//        return OrderItem.builder()
//                .itemId(orderItemRequestDto.getItemId())
//                .paymentPrice(orderItemRequestDto.getPaymentPrice())
//                .quantity(orderItemRequestDto.getQuantity())
//                .build();
//    }
}
