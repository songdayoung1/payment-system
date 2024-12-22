package kr.co.store.api.store.order.service;

import kr.co.store.api.store.domain.order.entity.User;
import kr.co.store.api.store.order.dto.OrderProductRequestDto;
import kr.co.store.api.store.order.dto.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private final UserService userService;
    private final ProductService productService;

    //  주문 요청 값 검증
//    public void validateOrderRequest(OrderRequestDto orderRequest) {
//        if (orderRequest.getOrderProducts() == null || orderRequest.getOrderProducts().isEmpty()) {
//            throw new IllegalArgumentException("주문 요청에 상품 정보가 없습니다.");
//        }
//    }

    //  회원 검증
    public User validateUser(Long userId) {
        Optional<User> user = userService.verifyUser(userId);
        return user.orElseThrow(() -> new IllegalArgumentException("비회원입니다. 로그인 후 다시 시도해주세요."));
    }

    //  재고 검증
    public void validateStock(List<OrderProductRequestDto> orderProducts) {
        Map<Long, Integer> stockCheckResult = productService.checkStock(orderProducts);
        if (!stockCheckResult.isEmpty()) {
            throw new IllegalArgumentException("재고 부족: " + stockCheckResult);
        }
    }
}
