package org.example.order.service.client;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.example.order.dto.PaymentRequest;
import org.example.order.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "delivery-service", url = "http://localhost:8083/delivery/api/payments")
public interface DeliveryFeignClientService {

    @PostMapping
    PaymentResponse reserveTime(@RequestParam Long Id, @RequestBody PaymentRequest req);
}