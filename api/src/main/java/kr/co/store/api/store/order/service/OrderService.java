package kr.co.store.api.store.order.service;

import kr.co.store.api.store.domain.order.entity.Order;
import kr.co.store.api.store.domain.order.repository.OrderRepository;
import kr.co.store.api.store.domain.order.types.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // 1. 페이먼트 ID로 주문 조회
    @Transactional(readOnly = true)
    public Optional<Order> getOrderByPaymentId(Long paymentId) {
        return Optional.ofNullable(orderRepository.findByPaymentId(paymentId));
    }

    // 2. 페이먼트 ID와 상태로 주문 조회
    @Transactional(readOnly = true)
    public Optional<Order> getOrderByPaymentIdAndStatus(Long paymentId, OrderStatus status) {
        return orderRepository.findByPaymentIdAndStatus(paymentId, status);
    }

    // 3. 주문 상태 업데이트
    @Transactional
    public void updateOrderStatus(Long orderId, OrderStatus newStatus) {
        orderRepository.updateOrderStatus(orderId, newStatus);
    }

    // 4. 페이먼트 ID로 주문 상태 업데이트
    @Transactional
    public void updateOrderStatusByPaymentId(Long paymentId, OrderStatus newStatus) {
        orderRepository.updateOrderStatusByPaymentId(paymentId, newStatus);
    }
}
