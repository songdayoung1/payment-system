package kr.co.store.api.store.domain.order.repository;

import kr.co.store.api.store.domain.order.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

}
