package com.example.orderc8.worker;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class ChargeCustomerWithRetryWorker {

    private final PaymentGateway gateway;

    public ChargeCustomerWithRetryWorker(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    @JobWorker(type = "charge-customer-with-retry", fetchVariables = {"orderId", "amount"})
    public Map<String, Object> chargeWithRetry(@Variable String orderId, @Variable BigDecimal amount) {
        return Map.of("chargeId", gateway.chargeWithBackoff(orderId, amount));
    }
}
