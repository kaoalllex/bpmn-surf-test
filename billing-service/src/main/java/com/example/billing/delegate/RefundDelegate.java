package com.example.billing.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/** The delegate Dunning.bpmn actually calls via ${refundDelegate}. */
@Component
public class RefundDelegate implements JavaDelegate {

    private final RefundService refundService;

    public RefundDelegate(RefundService refundService) {
        this.refundService = refundService;
    }

    @Override
    public void execute(DelegateExecution execution) {
        refundService.refund((String) execution.getVariable("chargeId"));
    }
}
