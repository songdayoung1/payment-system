package kr.co.store.api.store.order.controller;

import jakarta.validation.Valid;
import kr.co.store.api.store.order.dto.OrderRequestDto;
import kr.co.store.api.store.order.dto.OrderResponseDto;
import kr.co.store.api.store.order.service.OrderV2Service;
import kr.co.store.api.store.order.service.ProductService;
import kr.co.store.api.store.order.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v2/orders")
@RequiredArgsConstructor
public class OrderV2Controller {

    private final OrderV2Service orderV2Service;
    private final UserService userService;
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequestDto orderRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("orderRequest is invalid.");
        }
        OrderResponseDto orderResponse = orderV2Service.createOrder(orderRequest);
        return ResponseEntity.ok(orderResponse);
    }
}
