package com.example.billing.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/** Referenced from Billing.bpmn by its fully qualified camunda:class. */
public class IssueInvoiceDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        execution.setVariable("invoiceNumber", InvoiceNumbers.next());
    }
}
