package kr.co.store.api.store.order.type;

import kr.co.store.api.store.domain.common.IEnumtype;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentAmount {
    private int total;       // 총 결제금액
    private int taxFree;     // 면세액
    private int discount;    // 할인금액

    public PaymentAmount(int total, int taxFree, int discount) {
        this.total = total;
        this.taxFree = taxFree;
        this.discount = discount;
    }
}
