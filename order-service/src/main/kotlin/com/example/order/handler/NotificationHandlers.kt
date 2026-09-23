package com.example.order.handler

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskHandler
import org.camunda.bpm.client.task.ExternalTaskService
import org.springframework.stereotype.Component

/** Two topics served from a single file. */
@Component
@ExternalTaskSubscription("notifyCustomer")
class NotifyCustomerHandler(
    private val notifier: Notifier
) : ExternalTaskHandler {

    override fun execute(task: ExternalTask, service: ExternalTaskService) {
        notifier.send(task.getVariable("customerId"), "ORDER_COMPLETED")
        service.complete(task)
    }
}

@Component
@ExternalTaskSubscription("cancelOrder")
class CancelOrderHandler(
    private val orderRepository: OrderRepository
) : ExternalTaskHandler {

    override fun execute(task: ExternalTask, service: ExternalTaskService) {
        orderRepository.cancel(task.getVariable("orderId"))
        service.complete(task)
    }
}
