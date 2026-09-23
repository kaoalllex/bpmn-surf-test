package com.example.order.handler

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskHandler
import org.camunda.bpm.client.task.ExternalTaskService
import org.springframework.stereotype.Component

@Component
@ExternalTaskSubscription("checkReturnEligibility")
class CheckReturnEligibilityHandler(
    private val returnPolicy: ReturnPolicy
) : ExternalTaskHandler {

    override fun execute(task: ExternalTask, service: ExternalTaskService) {
        val eligible = returnPolicy.allows(task.getVariable("orderId"))
        service.complete(task, mapOf("eligible" to eligible))
    }
}
