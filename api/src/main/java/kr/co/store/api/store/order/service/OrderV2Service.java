package kr.co.store.api.store.order.service;

import kr.co.store.api.store.domain.order.entity.Order;
import kr.co.store.api.store.domain.order.entity.OrderProduct;
import kr.co.store.api.store.domain.order.entity.User;
import kr.co.store.api.store.domain.order.repository.OrderRepository;
import kr.co.store.api.store.domain.order.repository.UserRepository;
import kr.co.store.api.store.domain.order.types.OrderProductStatus;
import kr.co.store.api.store.domain.order.types.OrderStatus;
import kr.co.store.api.store.order.dto.OrderRequestDto;
import kr.co.store.api.store.order.dto.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderV2Service {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ValidationService validationService;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequest) {

        User user = validationService.validateUser(orderRequest.getUserId());
        validationService.validateStock(orderRequest.getOrderProducts());

        // 1. 고유 paymentId 생성
        Long paymentId = 1L;

        // 2. 오더 객체 생성
        Order order = Order.builder()
                .status(OrderStatus.PAYMENT_PENDING)
                .orderAt(LocalDateTime.now())
                .totalAmount(orderRequest.getTotalAmount())
                .activated(false)
                .user(user)
                .build();

        orderRequest.getOrderProducts().forEach(productRequest -> {

            OrderProduct orderProduct = OrderProduct.builder()
                    .status(OrderProductStatus.PAYMENT_PENDING)
//                    .productId(productRequest.getOrderProductId())
                    .paymentPrice(productRequest.getPaymentPrice())
                    .quantity(productRequest.getQuantity())
                    .activated(false)
                    .build();

            order.addOrderProduct(orderProduct);

        });


        // 3. DB 저장
        orderRepository.save(order);


        // 4. 응답값 생성
        OrderResponseDto orderResponse = OrderResponseDto.builder()
                .storeId("STORE_ID")
                .channelKey("PG_CHANNEL_KEY")
                .paymentId(paymentId)
                .user(user)
//                .orderProduct(order)
                .order(order)
                .build();


        return orderResponse;
    }

}
