package com.example.orderc8.worker

import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.spring.client.annotation.JobWorker
import io.camunda.zeebe.spring.client.annotation.Variable
import org.springframework.stereotype.Component

@Component
class NotificationWorkers(
    private val notifier: Notifier,
    private val orderRepository: OrderRepository
) {

    @JobWorker(type = "notify-customer")
    fun notifyCustomer(job: ActivatedJob, @Variable customerId: String) {
        notifier.send(customerId, job.customHeaders["template"] ?: "ORDER_COMPLETED")
    }

    @JobWorker(type = "cancel-order")
    fun cancelOrder(@Variable orderId: String) {
        orderRepository.cancel(orderId)
    }
}
