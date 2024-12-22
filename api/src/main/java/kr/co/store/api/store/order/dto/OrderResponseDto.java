package kr.co.store.api.store.order.dto;

import kr.co.store.api.store.domain.order.entity.Order;
import kr.co.store.api.store.domain.order.entity.OrderProduct;
import kr.co.store.api.store.domain.order.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class OrderResponseDto {

    private String storeId;
    private String channelKey;
    private Long paymentId;
    private User user;
    private OrderProduct orderProduct;
    private Order order;


    @Builder
    public OrderResponseDto(String storeId, String channelKey, Long paymentId, User user, OrderProduct orderProduct, Order order) {
        this.storeId = storeId;
        this.channelKey = channelKey;
        this.paymentId = paymentId;
        this.user = user;
        this.orderProduct = orderProduct;
        this.order = order;
    }
}
