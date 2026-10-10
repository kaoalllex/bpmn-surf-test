package com.example.orderc8.worker

import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.spring.client.annotation.JobWorker
import io.camunda.zeebe.spring.client.annotation.Variable
import org.springframework.stereotype.Component

/** "type" is not the first argument. */
@Component
class ReserveStockWorker(
    private val stock: StockService
) {

    @JobWorker(timeout = 30_000, type = "reserve-stock")
    fun reserve(@Variable orderId: String) {
        stock.reserve(orderId)
    }
}
