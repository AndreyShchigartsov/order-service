package org.example.order.service.client;

import lombok.RequiredArgsConstructor;
import org.example.order.dto.PaymentRequest;
import org.example.order.dto.PaymentResponse;
import org.example.order.exception.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryClientService {

    private final DeliveryFeignClientService deliveryFeignClientService;

    @Retryable(value = {RemoteAccessException.class}, maxAttempts = 3,
            backoff = @Backoff(delay = 2000))
    public PaymentResponse callExternalApi(Long id, PaymentRequest paymentRequest) {
        return deliveryFeignClientService.reserveTime(id, paymentRequest);
    }

    @Recover
    public String fallback(RemoteAccessException e) {
        return "Все попытки исчерпаны. Сервис недоступен.";
    }

}