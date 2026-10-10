package com.example.orderc8.worker

import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.spring.client.annotation.JobWorker
import io.camunda.zeebe.spring.client.annotation.Variable
import org.springframework.stereotype.Component

@Component
class ValidateOrderWorker(
    private val orderRepository: OrderRepository
) {

    @JobWorker(type = "validate-order")
    fun validate(@Variable orderId: String): Map<String, Any> {
        val order = orderRepository.load(orderId)
        return mapOf("valid" to order.isComplete())
    }
}
