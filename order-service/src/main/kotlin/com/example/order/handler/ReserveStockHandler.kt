package com.example.order.handler

import com.example.platform.camunda.WrapToExternalTask
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.springframework.stereotype.Component

/**
 * No topic is stated: the platform derives it from the class name,
 * so this handler serves the topic "reserveStockHandler".
 */
@Component
@WrapToExternalTask(retriesTimeout = 60_000)
class ReserveStockHandler(
    private val warehouse: WarehouseClient
) {
    fun execute(execution: DelegateExecution) {
        val orderId = execution.getVariable("orderId") as String
        execution.setVariable("reservationId", warehouse.reserve(orderId))
    }
}
