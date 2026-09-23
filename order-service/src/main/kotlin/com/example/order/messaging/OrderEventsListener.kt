package com.example.order.messaging

import org.camunda.bpm.engine.RuntimeService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class OrderEventsListener(
    private val runtimeService: RuntimeService
) {

    /** Case A: the message name sits literally at the correlation site. */
    @KafkaListener(topics = ["order.payments"])
    fun onPaymentReceived(event: PaymentEvent) {
        runtimeService.createMessageCorrelation("OrderPaid")
            .processInstanceBusinessKey(event.orderId)
            .setVariable("paid", true)
            .correlateWithResult()
    }
}
