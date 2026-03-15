package org.example.orderservice.aggregate;

import lombok.Getter;
import org.example.orderservice.entity.Order;
import org.example.orderservice.valueobject.OrderStatus;

@Getter
public class OrderAggregate {
    private final Order order;
    private boolean canBeCancelled;
    private boolean canBePaid;
    private boolean canBeShipped;

    public OrderAggregate(Order order) {
        this.order = order;
        validateState();
    }

    private void validateState() {
        this.canBeCancelled = order.getStatus() == OrderStatus.CREATED;
        this.canBePaid = order.getStatus() == OrderStatus.CREATED;
        this.canBeShipped = order.getStatus() == OrderStatus.PAID;
    }

    public OrderAggregate markAsPaid() {
        if (!canBePaid) {
            throw new IllegalStateException("Order cannot be paid");
        }
        return new OrderAggregate(
                Order.builder()
                        .id(order.getId())
                        .orderNumber(order.getOrderNumber())
                        .status(OrderStatus.PAID)
                        .totalAmount(order.getTotalAmount())
                        .customerInfo(order.getCustomerInfo())
                        .deliveryAddress(order.getDeliveryAddress())
                        .paymentId(order.getPaymentId())
                        .build()
        );
    }
}
