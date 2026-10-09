package com.example.order.handler

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskHandler
import org.camunda.bpm.client.task.ExternalTaskService
import org.springframework.stereotype.Component

@Component
@ExternalTaskSubscription(topicName = "checkFraud", lockDuration = 30_000)
class CheckFraudHandler(
    private val fraudClient: FraudClient
) : ExternalTaskHandler {

    override fun execute(task: ExternalTask, service: ExternalTaskService) {
        val score = fraudClient.score(task.getVariable("orderId"))
        service.complete(task, mapOf("fraudScore" to score, "suspicious" to (score > 80)))
    }
}

// code-only MR: no diagram here
