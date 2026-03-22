package org.example.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.order.aggregate.OrderAggregate;
import org.example.order.dto.OrderRequest;
import org.example.order.dto.OrderResponse;
import org.example.order.entity.Order;
import org.example.order.repository.OrderRepository;
import org.example.order.valueobject.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        Order order = Order.builder()
                .orderNumber(generateOrderNumber())
                .status(OrderStatus.CREATED)
                .totalAmount(request.getTotalAmount())
                .customerInfo(request.getCustomerInfo())
                .deliveryAddress(request.getDeliveryAddress())
                .paymentId(request.getPaymentId())
                .build();

        Order savedOrder = orderRepository.save(order);
        log.info("Order created: {}", savedOrder.getOrderNumber());

        return OrderResponse.fromEntity(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrder(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return OrderResponse.fromEntity(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse updateOrder(UUID id, OrderRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Order updatedOrder = Order.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .status(request.getStatus() != null ? request.getStatus() : order.getStatus())
                .totalAmount(request.getTotalAmount() != null ? request.getTotalAmount() : order.getTotalAmount())
                .customerInfo(request.getCustomerInfo() != null ? request.getCustomerInfo() : order.getCustomerInfo())
                .deliveryAddress(request.getDeliveryAddress() != null ? request.getDeliveryAddress() : order.getDeliveryAddress())
                .paymentId(request.getPaymentId() != null ? request.getPaymentId() : order.getPaymentId())
                .build();

        Order savedOrder = orderRepository.save(updatedOrder);
        return OrderResponse.fromEntity(savedOrder);
    }

    @Transactional
    public void deleteOrder(UUID id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(id);
        log.info("Order deleted: {}", id);
    }

    @Transactional
    public OrderResponse updateOrderStatus(UUID id, OrderStatus newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderAggregate aggregate = new OrderAggregate(order);

        Order updatedOrder;
        if (newStatus == OrderStatus.PAID) {
            updatedOrder = aggregate.markAsPaid().getOrder();
        } else {
            updatedOrder = Order.builder()
                    .id(order.getId())
                    .orderNumber(order.getOrderNumber())
                    .status(newStatus)
                    .totalAmount(order.getTotalAmount())
                    .customerInfo(order.getCustomerInfo())
                    .deliveryAddress(order.getDeliveryAddress())
                    .paymentId(order.getPaymentId())
                    .build();
        }

        Order savedOrder = orderRepository.save(updatedOrder);
        return OrderResponse.fromEntity(savedOrder);
    }

    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis();
    }
}
