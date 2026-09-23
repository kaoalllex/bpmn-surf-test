package com.example.order.handler

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskHandler
import org.camunda.bpm.client.task.ExternalTaskService
import org.springframework.stereotype.Component

@Component
@ExternalTaskSubscription("validateOrder")
class ValidateOrderHandler(
    private val orderRepository: OrderRepository
) : ExternalTaskHandler {

    override fun execute(task: ExternalTask, service: ExternalTaskService) {
        val orderId = task.getVariable<String>("orderId")
        val order = orderRepository.find(orderId)
            ?: return service.handleBpmnError(task, "ORDER_NOT_FOUND")

        val valid = order.positions.isNotEmpty() && order.total > 0
        service.complete(task, mapOf("orderValid" to valid))
    }
}
