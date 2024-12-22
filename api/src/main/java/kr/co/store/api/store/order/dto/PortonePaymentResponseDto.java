package kr.co.store.api.store.order.dto;

import kr.co.store.api.store.order.type.PaymentAmount;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PortonePaymentResponseDto {

    private String transactionId;
    private String status;
    private String id;
    private PaymentAmount amount;

    @Builder
    public PortonePaymentResponseDto(String transactionId, String status, String id, PaymentAmount amount) {
        this.transactionId = transactionId;
        this.status = status;
        this.id = id;
        this.amount = amount;
    }
}

