package com.example.order.handler

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskHandler
import org.camunda.bpm.client.task.ExternalTaskService
import org.springframework.stereotype.Component

@Component
@ExternalTaskSubscription(topicName = "reserveStock", lockDuration = 60_000)
class ReserveStockHandler(
    private val warehouse: WarehouseClient
) : ExternalTaskHandler {

    override fun execute(task: ExternalTask, service: ExternalTaskService) {
        val orderId = task.getVariable<String>("orderId")
        service.complete(task, mapOf("reservationId" to warehouse.reserve(orderId)))
    }
}
