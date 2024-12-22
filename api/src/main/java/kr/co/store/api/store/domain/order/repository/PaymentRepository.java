package kr.co.store.api.store.domain.order.repository;

import kr.co.store.api.store.domain.order.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentUid(String paymentUid);

    // PaymentUid로 활성화 변경
    @Modifying
    @Query("UPDATE Payment p SET p.activated = :activated WHERE p.paymentUid = :paymentUid")
    void updatePaymentStatus(@Param("paymentUid") String paymentUid, @Param("activated") Boolean activated);

}
