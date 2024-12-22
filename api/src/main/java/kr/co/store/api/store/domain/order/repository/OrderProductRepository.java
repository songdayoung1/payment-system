package kr.co.store.api.store.domain.order.repository;

import kr.co.store.api.store.domain.order.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>{
}
