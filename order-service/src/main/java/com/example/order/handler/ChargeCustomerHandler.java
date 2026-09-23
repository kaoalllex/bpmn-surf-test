package com.example.order.handler;

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.stereotype.Component;

import java.util.Map;

/** The Java flavour of an external-task handler, next to the Kotlin ones. */
@Component
@ExternalTaskSubscription("chargeCustomer")
public class ChargeCustomerHandler implements ExternalTaskHandler {

    private final PaymentGateway gateway;

    public ChargeCustomerHandler(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(ExternalTask task, ExternalTaskService service) {
        String orderId = task.getVariable("orderId");
        String chargeId = gateway.charge(orderId, task.getVariable("amount"));
        service.complete(task, Map.of("chargeId", chargeId));
    }
}
