package kr.co.store.api.store.domain.order.types;

public enum OrderStatus {
    PAYMENT_PENDING,         // 결제 대기
    PAYMENT_COMPLETED,       // 결제 완료
    DELIVERY_READY,          // 배송 준비 중
    DELIVERY_PROGRESS,       // 배송 중
    DELIVERY_COMPLETED,      // 배송 완료
    CANCEL,                  // 주문 취소
    COMPLETED                // 거래 완료
}