package kr.co.store.api.init;

import kr.co.store.api.store.domain.order.entity.Order;
import kr.co.store.api.store.domain.order.entity.OrderProduct;
import kr.co.store.api.store.domain.order.repository.OrderProductRepository;
import kr.co.store.api.store.domain.order.repository.OrderRepository;
import kr.co.store.api.store.domain.order.repository.ProductRepository;
import kr.co.store.api.store.domain.order.repository.UserRepository;
import kr.co.store.api.store.domain.order.types.OrderProductStatus;
import kr.co.store.api.store.domain.order.types.OrderStatus;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//@Component
public class OrderDataGenerator implements CommandLineRunner {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderDataGenerator(OrderRepository orderRepository,
                              OrderProductRepository orderProductRepository,
                              ProductRepository productRepository,
                              UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        Random random = new Random();
        List<Order> orders = new ArrayList<>();
        List<OrderProduct> orderProducts = new ArrayList<>();

        Long userCount = userRepository.count();
        Long productCount = productRepository.count();

        for (int i = 0; i < 100000; i++) {
            // 랜덤 상태 설정
            OrderStatus randomOrderStatus = getRandomOrderStatus(random);
            OrderProductStatus randomOrderProductStatus = getRandomOrderProductStatus(random);

            // 랜덤 유저 선택
            Long randomUserId = 1L + random.nextInt(userCount.intValue());
            Long randomProductId = 1L + random.nextInt(productCount.intValue());

            // Order 데이터 생성
            Order order = Order.builder()
                    .status(randomOrderStatus)
                    .orderAt(LocalDateTime.now().minusDays(random.nextInt(365))) // 최근 1년 날짜
                    .totalAmount(random.nextInt(100000) + 1000) // 1000 ~ 100000 랜덤 금액
                    .activated(true)
                    .user(userRepository.findById(randomUserId).orElseThrow())
                    .build();

            orders.add(order);

            // OrderProduct 데이터 생성 (1~5개 랜덤)
            int productCountPerOrder = random.nextInt(5) + 1;
            for (int j = 0; j < productCountPerOrder; j++) {
                OrderProduct orderProduct = OrderProduct.builder()
                        .status(randomOrderProductStatus)
                        .paymentPrice(random.nextInt(10000) + 500) // 500~10,000 랜덤 결제 금액
                        .quantity(random.nextInt(5) + 1) // 수량 1~5개 랜덤
                        .activated(true)
                        .order(order)
                        .product(productRepository.findById(randomProductId).orElseThrow())
                        .build();

                orderProducts.add(orderProduct);
            }

            // 1000개 단위로 Batch Insert
            if (i % 1000 == 0) {
                orderRepository.saveAll(orders);
                orderProductRepository.saveAll(orderProducts);
                orders.clear();
                orderProducts.clear();
                System.out.println(i + " records inserted...");
            }
        }

        // 남은 데이터 저장
        orderRepository.saveAll(orders);
        orderProductRepository.saveAll(orderProducts);
        System.out.println("Data generation completed successfully!");
    }

    private OrderStatus getRandomOrderStatus(Random random) {
        OrderStatus[] statuses = OrderStatus.values();
        return statuses[random.nextInt(statuses.length)];
    }

    private OrderProductStatus getRandomOrderProductStatus(Random random) {
        OrderProductStatus[] statuses = OrderProductStatus.values();
        return statuses[random.nextInt(statuses.length)];
    }
}