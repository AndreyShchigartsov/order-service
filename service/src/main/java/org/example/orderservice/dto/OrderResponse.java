package org.example.orderservice.dto;

import lombok.Builder;
import lombok.Data;
import org.example.orderservice.entity.Order;
import org.example.orderservice.valueobject.CustomerInfo;
import org.example.orderservice.valueobject.Money;
import org.example.orderservice.valueobject.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class OrderResponse {
    private UUID id;
    private String orderNumber;
    private OrderStatus status;
    private Money totalAmount;
    private CustomerInfo customerInfo;
    private String deliveryAddress;
    private String paymentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderResponse fromEntity(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .customerInfo(order.getCustomerInfo())
                .deliveryAddress(order.getDeliveryAddress())
                .paymentId(order.getPaymentId())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}
