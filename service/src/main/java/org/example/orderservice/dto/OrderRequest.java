package org.example.orderservice.dto;

import lombok.Data;
import org.example.orderservice.valueobject.CustomerInfo;
import org.example.orderservice.valueobject.Money;
import org.example.orderservice.valueobject.OrderStatus;

@Data
public class OrderRequest {
    private Money totalAmount;
    private CustomerInfo customerInfo;
    private String deliveryAddress;
    private String paymentId;
    private OrderStatus status;
}
