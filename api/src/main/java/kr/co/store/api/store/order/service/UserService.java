package kr.co.store.api.store.order.service;

import kr.co.store.api.store.domain.order.entity.User;
import kr.co.store.api.store.domain.order.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> verifyUser(Long userId) {

        if (userId == null || userId <= 0) {
            return Optional.empty(); // 비회원 처리
        }
        // return userRepository.findById(userId);
        return null;
    }


}
