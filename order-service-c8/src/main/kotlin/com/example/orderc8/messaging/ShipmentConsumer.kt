package com.example.orderc8.messaging

import io.camunda.zeebe.client.ZeebeClient
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ShipmentConsumer(
    private val zeebeClient: ZeebeClient
) {

    /** Synchronous correlation (8.6+): the engine answers whether a subscription matched. */
    @KafkaListener(topics = ["shipments"])
    fun onShipmentConfirmed(event: ShipmentEvent) {
        zeebeClient.newCorrelateMessageCommand()
            .messageName("ShipmentConfirmedC8")
            .correlationKey(event.shipmentId)
            .send()
            .join()
    }
}
