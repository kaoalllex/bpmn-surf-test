package com.example.orderc8.messaging

import io.camunda.zeebe.client.ZeebeClient
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class PaymentEventsListener(
    private val zeebeClient: ZeebeClient
) {

    /** The message name sits literally at the publish site. */
    @KafkaListener(topics = ["order.payments"])
    fun onPaymentReceived(event: PaymentEvent) {
        zeebeClient.newPublishMessageCommand()
            .messageName("OrderPaidC8")
            .correlationKey(event.orderId)
            .variables(mapOf("paid" to true))
            .send()
    }
}
