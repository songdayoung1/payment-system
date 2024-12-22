package kr.co.store.api.store.domain.order.entity;

import jakarta.persistence.*;
import kr.co.store.api.store.domain.common.BaseEntity;
import kr.co.store.api.store.domain.order.types.OrderProductStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Entity
@Table(name = "ORDER_PRODUCT")
public class OrderProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderProductStatus status;

    @Column(name = "payment_price", nullable = false)
    private Integer paymentPrice;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Boolean activated;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @Builder
    public OrderProduct(OrderProductStatus status, Integer paymentPrice, Integer quantity, Boolean activated, Order order, Product product) {
        this.status = status;
        this.paymentPrice = paymentPrice;
        this.quantity = quantity;
        this.activated = activated;
        this.product = product;
        if (order != null) {
            this.addOrder(order);
        }
    }

    public void addOrder(Order order) {
        this.order = order;
        if (!order.getOrderProducts().contains(this)) {
            order.getOrderProducts().add(this);
        }
    }

}
