package org.example.order.service.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.example.order.dto.PaymentRequest;
import org.example.order.dto.PaymentResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentClientService {

    private final PaymentFeignClientService paymentFeignClientService;

    @Retry(name = "doctorServiceRetry")
    @CircuitBreaker(name = "paymentService", fallbackMethod = "fallback")
    public PaymentResponse callExternalApi(Long id, PaymentRequest paymentRequest) {
        return paymentFeignClientService.reserveTime(id, paymentRequest);
    }
    public PaymentResponse fallback(Throwable t) {
        return PaymentResponse.builder().build();
    }


}
