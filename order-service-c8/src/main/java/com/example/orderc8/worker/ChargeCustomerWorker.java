package com.example.orderc8.worker;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

/** The Java flavour of a job worker; "charge-customer" is a prefix of "charge-customer-with-retry". */
@Component
public class ChargeCustomerWorker {

    private final PaymentGateway gateway;

    public ChargeCustomerWorker(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    @JobWorker(type = "charge-customer")
    public Map<String, Object> charge(@Variable String orderId, @Variable BigDecimal amount) {
        String chargeId = gateway.charge(orderId, amount);
        return Map.of("chargeId", chargeId);
    }
}
