package com.example.billing.legacy;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * Same simple name as com.example.billing.delegate.RefundDelegate, different
 * package: handler resolution matches by simple name, so class:RefundDelegate
 * is ambiguous here on purpose.
 */
@Deprecated
public class RefundDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        throw new UnsupportedOperationException("replaced by the delegate package");
    }
}
