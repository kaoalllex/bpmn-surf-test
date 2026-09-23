package com.example.billing.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/**
 * Referenced from Billing.bpmn as ${chargeCardDelegate} — the Spring bean name
 * is the class name with a lower-cased first letter.
 */
@Component
public class ChargeCardDelegate implements JavaDelegate {

    private final CardGateway cardGateway;

    public ChargeCardDelegate(CardGateway cardGateway) {
        this.cardGateway = cardGateway;
    }

    @Override
    public void execute(DelegateExecution execution) {
        String contractId = (String) execution.getVariable("contractId");
        boolean threeDs = Boolean.TRUE.equals(execution.getVariable("threeDsRequired"));
        execution.setVariable("chargeId", cardGateway.charge(contractId, threeDs));
    }
}
