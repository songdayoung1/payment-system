package kr.co.store.api.store.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentRequsetDto {

    private String transactionType;

    @NotNull(message = "userId is not null")
    private String txId;

    @NotNull(message = "userId is not null")
    private Long paymentId;

    private String code;

    private String message;


    @Builder
    public PaymentRequsetDto(String transactionType, String txId, Long paymentId, String code, String message) {
        this.transactionType = transactionType;
        this.txId = txId;
        this.paymentId = paymentId;
        this.code = code;
        this.message = message;
    }
}
