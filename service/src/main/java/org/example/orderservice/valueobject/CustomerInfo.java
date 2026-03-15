package org.example.orderservice.valueobject;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfo {
    private String customerId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
}
