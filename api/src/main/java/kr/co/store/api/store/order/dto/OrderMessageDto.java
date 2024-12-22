package kr.co.store.api.store.order.dto;

import kr.co.store.api.store.domain.order.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderMessageDto {
    private Long orderId;

    public OrderMessageDto(Long orderId) {
        this.orderId = orderId;
    }

}
