package com.example.orderc8.worker

import io.camunda.zeebe.spring.client.annotation.Variable
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker
import org.springframework.stereotype.Component

/** The deprecated spring-zeebe annotation; found only when "ZeebeWorker" is configured. */
@Component
class EscalateDeliveryWorker(
    private val dispatch: DispatchClient
) {

    @ZeebeWorker(type = "escalate-delivery")
    fun escalate(@Variable orderId: String) {
        dispatch.escalate(orderId)
    }
}
