package com.example.order.handler;

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Serves the topic Payment.bpmn moved to. Its name deliberately starts with the
 * topic of ChargeCustomerHandler, so a blob search for "chargeCustomer" returns
 * both and the exact-match gate (BUG-0027) has something to gate — the Java
 * counterpart of the findItems / findItemsInCatalog pair.
 */
@Component
@ExternalTaskSubscription("chargeCustomerWithRetry")
public class ChargeCustomerWithRetryHandler implements ExternalTaskHandler {

    private final PaymentGateway gateway;

    public ChargeCustomerWithRetryHandler(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(ExternalTask task, ExternalTaskService service) {
        String orderId = task.getVariable("orderId");
        for (int attempt = 1; attempt <= 3; attempt++) {
            String chargeId = gateway.charge(orderId, task.getVariable("amount"));
            if (chargeId != null) {
                service.complete(task, Map.of("chargeId", chargeId, "attempts", attempt));
                return;
            }
        }
        service.handleFailure(task, "charge failed", "no successful attempt", 0, 0);
    }
}
