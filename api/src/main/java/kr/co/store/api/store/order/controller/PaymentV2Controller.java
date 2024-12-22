package kr.co.store.api.store.order.controller;


import jakarta.validation.Valid;
import kr.co.store.api.store.order.dto.PaymentRequsetDto;
import kr.co.store.api.store.order.dto.PortonePaymentResponseDto;
import kr.co.store.api.store.order.service.PaymentV2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/payment")
@RequiredArgsConstructor
public class PaymentV2Controller {

    private final PaymentV2Service paymentV2Service;

    // PC 용
    @PostMapping("callback")
    public ResponseEntity<?> paymentCallback(@Valid @RequestBody PaymentRequsetDto payRequsetDto, BindingResult bingdingResult ){

        if(bingdingResult.hasErrors()){
            String errorMessage = bingdingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));

            return ResponseEntity.badRequest().body("validation error => " + errorMessage);
        }

        return ResponseEntity.ok("callback : payment is confirm");
    }

    // 모바일 용
    @PostMapping("redirect")
    public ResponseEntity<?> paymentRedirect(@RequestBody PaymentRequsetDto payRequsetDto){

        return ResponseEntity.ok("callback : payment is confirm");
    }


    // 네트워크 유실로 인한 웹훅
    @PostMapping("webhook")
    public ResponseEntity<?> paymentWebhook(@RequestBody PaymentRequsetDto paymentRequsetDto, BindingResult bingdingResult ){

        // 1. 응답값 검증 확인
        if(bingdingResult.hasErrors()){
            String errorMessage = bingdingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));

            return ResponseEntity.badRequest().body("validation error => " + errorMessage);
        }


        try {
            // 2. 단건 조회 (결제 금액 비교)
            // "https://api.portone.io/v2/payment/" + paymentId + "?storeId=" + storeId

            // * 응답 예시 {
            // transactionId (String)
            // status(String) : CANCELLED (결제 취소), FAILED (결제 실패), PAID (결제 완료), PARTIAL_CANCELLED (결제 취소)
            // PAY_PENDING (결제 완료 대기), READY (결제 준비),  VIRTUAL_ACCOUNT_ISSUED (가상계좌 발급 완료) }

            // 포트원 API
            String portoneApiUrl = "https://api.portone.io/v2/payment/" + paymentRequsetDto.getPaymentId();

            // API 호출
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<PortonePaymentResponseDto> response = restTemplate.getForEntity(portoneApiUrl, PortonePaymentResponseDto.class);

            // 호출 실패
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                return ResponseEntity.badRequest().body("Failed to import a data from portone");
            }

            // 호출에 성공했다면 응답값 추출
            PortonePaymentResponseDto portonePayment = response.getBody();
            String transactionId = portonePayment.getTransactionId();
            String status = portonePayment.getStatus();

            if (transactionId == null || status == null) {
                return ResponseEntity.badRequest().body("transactionId or status is invalid.");
            }

            // 3. 결제 상태에 따른 디비 데이터 저장
             paymentV2Service.paymentConfirm(paymentRequsetDto, portonePayment);

            return ResponseEntity.ok("[payment webhook] : payment is complete");

        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.badRequest().body("[payment webhook] Error occurred =>  " + e.getMessage());
        }
    }
}
