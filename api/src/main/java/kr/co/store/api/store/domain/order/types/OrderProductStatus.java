package kr.co.store.api.store.domain.order.types;

public enum OrderProductStatus {
    PAYMENT_PENDING,     // 결제 대기
    PAYMENT_COMPLETED,   // 결제 완료
    RETURN_IN_PROGRESS,  // 반품 진행중
    RETURN_COMPLETED,    // 반품 완료
    REFUND_IN_PROGRESS,  // 환불 진행중
    REFUND_COMPLETED,    // 환불 완료

}