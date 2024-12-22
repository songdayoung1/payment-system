package kr.co.store.api.store.order.service;

import kr.co.store.api.store.domain.order.entity.User;
import kr.co.store.api.store.domain.order.repository.OrderRepository;
import kr.co.store.api.store.domain.order.repository.UserRepository;
import kr.co.store.api.store.order.dto.OrderRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

import com.navercorp.fixturemonkey.FixtureMonkey;

@ExtendWith(MockitoExtension.class)
class OrderV2ServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private OrderV2Service orderV2Service;

    private OrderRequestDto orderRequestDto;

    private User mockUser;

    @BeforeEach
    void setUp() {
        // 목업 데이터 준비
        MockitoAnnotations.openMocks(this);
//        FixtureMonkey fixtureMonkey = FixtureMonkey.builder();
    }

    @Test
    void createOrder() {
        // Given:
        // When:
        // Then:
        // Mock
    }
}