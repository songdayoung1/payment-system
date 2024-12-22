package kr.co.store.api.store.domain.order.entity;

import jakarta.persistence.*;
import kr.co.store.api.store.domain.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "payment_uid")
    private String paymentUid;

    @Column(name = "payment_code")
    private String paymentCode;

    @Column(name = "payment_price", nullable = false)
    private Integer paymentPrice;

    @Column(nullable = false)
    private Boolean activated;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Builder
    public Payment(Long id, String paymentUid, String paymentCode, Integer paymentPrice, Boolean activated, Order order) {
        this.id = id;
        this.paymentUid = paymentUid;
        this.paymentCode = paymentCode;
        this.paymentPrice = paymentPrice;
        this.activated = activated;
        if (order != null) {
            this.addOrder(order);
        }
    }

    public void addOrder(Order order) {
        this.order = order;
        if (order.getPayment() != this) {
            order.addPayment(this);
        }
    }
}
