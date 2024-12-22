package kr.co.store.api.store.domain.order.repository;


import kr.co.store.api.store.domain.order.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
