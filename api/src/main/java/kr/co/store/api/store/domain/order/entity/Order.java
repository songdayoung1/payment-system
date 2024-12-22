package kr.co.store.api.store.domain.order.entity;

import jakarta.persistence.*;
import kr.co.store.api.store.domain.common.BaseEntity;
import kr.co.store.api.store.domain.order.types.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity @Getter
@Table(name = "ORDERS")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(name = "order_at", nullable = false)
    private LocalDateTime orderAt;

    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;

    @Column(nullable = false)
    private Boolean activated;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;


    @Builder
    public Order(OrderStatus status, LocalDateTime orderAt, Integer totalAmount,
                 Boolean activated, User user, List<OrderProduct> orderProducts, Payment payment) {
        this.status = status;
        this.orderAt = orderAt;
        this.totalAmount = totalAmount;
        this.activated = activated;
    
        if(user != null){
            this.addUser(user);
        }

        if (orderProducts != null) {
            orderProducts.forEach(this::addOrderProduct);
        }
        if (payment != null) {
            this.addPayment(payment);
        }
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        if (!orderProducts.contains(orderProduct)) {
            orderProducts.add(orderProduct);
            orderProduct.addOrder(this);
        }
    }

    public void addPayment(Payment payment) {
        this.payment = payment;
        if (payment.getOrder() != this) {
            payment.addOrder(this);
        }
    }

    public void addUser(User user) {
        this.user = user;
        if(user.getOrders() != this) {
            user.addOrder(this);
        }
    }

    public void updateStatus(OrderStatus newStatus) {
        if (this.status != newStatus) {
            this.status = newStatus;
        }
    }


}
