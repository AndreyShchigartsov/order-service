package org.example.order.dto;

import lombok.Data;
import org.example.order.valueobject.CustomerInfo;
import org.example.order.valueobject.Money;
import org.example.order.valueobject.OrderStatus;

@Data
public class OrderRequest {
    private Money totalAmount;
    private CustomerInfo customerInfo;
    private String deliveryAddress;
    private String paymentId;
    private OrderStatus status;
}
