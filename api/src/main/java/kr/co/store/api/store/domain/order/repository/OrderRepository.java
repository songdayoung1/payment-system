package kr.co.store.api.store.domain.order.repository;

import kr.co.store.api.store.domain.order.entity.Order;
import kr.co.store.api.store.domain.order.types.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByOrderAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // 페이먼트 아이디로 주문 내역 확인
    Order findByPaymentId(@Param("paymentId") Long paymentId);

    // 페이먼트 아이디 + 상태로 주문 내역 확인
    @Query("SELECT o FROM Order o WHERE o.payment.id = :paymentId AND o.status = :status")
    Optional<Order> findByPaymentIdAndStatus(@Param("paymentId") Long paymentId, @Param("status") OrderStatus status);

    // 주문 상태 업데이트
    @Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :orderId")
    void updateOrderStatus(@Param("orderId") Long orderId, @Param("status") OrderStatus status);

    // 페이먼트 아이디로 주문 상태 업데이트
    @Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.payment.id = :paymentId")
    void updateOrderStatusByPaymentId(@Param("paymentId") Long paymentId, @Param("status") OrderStatus status);


}
