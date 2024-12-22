package kr.co.store.api.store.order.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class OrderRequestDto {

    @NotNull(message = "userId is not null")
    private Long userId;

    @NotNull(message = "paymentUid is not null")
    private String paymentUid;

    @NotNull(message = "orderProduct is not null")
    @Valid
    private List<OrderProductRequestDto> orderProducts;
    private int totalAmount;

    @Builder
    public OrderRequestDto(Long userId, String paymentUid, List<OrderProductRequestDto> orderProducts, int totalAmount) {
        this.paymentUid = paymentUid;
        this.userId = userId;
        this.orderProducts = orderProducts;
        this.totalAmount = totalAmount;
    }

}
