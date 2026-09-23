package com.example.order.messaging

import org.camunda.bpm.engine.RuntimeService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ShipmentConsumer(
    private val runtimeService: RuntimeService
) {

    /** Case B: the name reaches the correlation site through a constant. */
    @KafkaListener(topics = ["logistics.shipments"])
    fun onShipmentConfirmed(event: ShipmentEvent) {
        runtimeService.createMessageCorrelation(Messages.ORDER_SHIPPED)
            .processInstanceBusinessKey(event.orderId)
            .setVariable("shipmentId", event.shipmentId)
            .correlate()
    }
}
