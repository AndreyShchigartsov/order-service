package org.example.order.dto;

import lombok.Builder;
import lombok.Data;
import org.example.order.valueobject.Money;
import org.example.order.valueobject.PaymentMethod;
import org.example.order.valueobject.PaymentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class PaymentResponse {

    private UUID id;
    private String paymentNumber;
    private String orderId;
    private PaymentStatus status;
    private Money amount;
    private PaymentMethod paymentMethod;
    private String transactionId;
    private String paymentDetails;
    private String payerId;
    private String payerEmail;
    private String errorMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
}

