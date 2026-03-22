package org.example.order.service.client;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.example.order.dto.PaymentRequest;
import org.example.order.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "payment-service", url = "http://localhost:8084/payment/api/payments")
public interface PaymentFeignClientService {

    @PostMapping
    PaymentResponse reserveTime(@RequestParam Long doctorId, @RequestBody PaymentRequest req);
}