package kr.co.store.api.store.order.service;

import kr.co.store.api.store.domain.order.entity.Order;
import kr.co.store.api.store.domain.order.entity.Payment;
import kr.co.store.api.store.domain.order.repository.OrderRepository;
import kr.co.store.api.store.domain.order.repository.PaymentRepository;
import kr.co.store.api.store.domain.order.types.OrderStatus;
import kr.co.store.api.store.order.dto.PaymentRequsetDto;
import kr.co.store.api.store.order.dto.PortonePaymentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentV2Service {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    // 1. 결제 상태 (FAILED / PAID) 에 따라 디비 저장할 객체값 설정
    // Failed이면 금액 비교 로직 x

    // 2. 포트원 서버 조횐된 결제 금액과 orderRepository 디비 결제 금액  비교

    // 3. 결제 금액이 다르다면 취소 API를 통해 취소 처리
    // 결제 상태 및 금액이 다른 이유로 최소일 경우 : OrderStatus.CANCEL
    // 결제 상태 및 금액이 다른 이유로 완료일 경우 : OrderStatus.PAYMENT_COMPLETED

    // 4. 결제 상태에 따라 디비 저장

    @Transactional
    public String paymentConfirm(PaymentRequsetDto paymentRequsetDto, PortonePaymentResponseDto portonePayment){

        String status = portonePayment.getStatus();
        Long paymentId = paymentRequsetDto.getPaymentId();
        String paymentPaid = "PAID";
        String paymentFailed = "FAILED";

        // Order 조회
        Order order = orderRepository.findByPaymentId(paymentId);

        // 2. 포트원 서버 조회된 결제 금액과 Order 디비 결제 금액 비교
        Integer paymentTotal = portonePayment.getAmount().getTotal();
        boolean checkedAmount = !order.getTotalAmount().equals(paymentTotal);

        // 취소일 때는 취소 포트원 API 호출
        if (checkedAmount) {
            order.updateStatus(OrderStatus.CANCEL); // 금액 불일치: 취소 상태로 설정
        } else if (paymentPaid.equals(status)) {
            order.updateStatus(OrderStatus.PAYMENT_COMPLETED); // 결제 완료 상태
        } else if (paymentFailed.equals(status)) {
            order.updateStatus(OrderStatus.CANCEL); // 결제 실패 상태
        } else {
            order.updateStatus(OrderStatus.CANCEL);
        }

        // 3. Payment 객체 생성 및 저장
        Payment payment = Payment.builder()
                .paymentUid(paymentRequsetDto.getTxId())
                .paymentCode("PAYMENT_CODE")
                .paymentPrice(paymentTotal)
                .activated(true)
                .order(order)
                .build();

        // 4. 디비 저장
        paymentRepository.save(payment);
        orderRepository.save(order);


        return "Payment processing complete. Status: " + status;
    }
}
