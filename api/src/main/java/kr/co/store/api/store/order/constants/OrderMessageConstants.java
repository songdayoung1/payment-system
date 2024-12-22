package kr.co.store.api.store.order.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderMessageConstants {
    public static final String ORDER_CREATED_TOPIC = "develop.commerce.order.created";
}
