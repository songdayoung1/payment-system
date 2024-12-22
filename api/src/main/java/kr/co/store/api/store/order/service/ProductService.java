package kr.co.store.api.store.order.service;

import kr.co.store.api.store.domain.order.repository.ProductRepository;
import kr.co.store.api.store.order.dto.OrderProductRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Map<Long, Integer> checkStock(List<OrderProductRequestDto> orderProducts) {
        Map<Long, Integer> emptyStock = new HashMap<>();

        for (OrderProductRequestDto product : orderProducts) {
            // 남은 재고 조회
            //  int stock = productRepository.findStockByProductId(product.getOrderProductId());
            int stock = 50;
            // 한 주문에 한 상품이라도 재고가 없다면 주문 취소
            if (product.getQuantity() > stock) {
                ((HashMap<Long, Integer>) emptyStock).put(product.getOrderProductId(), stock);
            }
        }

        // 재고 부족 상품 ID와 남은 재고량 반환
        return emptyStock;
    }

}
