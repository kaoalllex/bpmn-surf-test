package com.example.billing.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/** Added together with the "Send the receipt" task in Billing.bpmn. */
@Component
public class SendReceiptDelegate implements JavaDelegate {

    private final ReceiptSender sender;

    public SendReceiptDelegate(ReceiptSender sender) {
        this.sender = sender;
    }

    @Override
    public void execute(DelegateExecution execution) {
        sender.send((String) execution.getVariable("invoiceNumber"));
    }
}
