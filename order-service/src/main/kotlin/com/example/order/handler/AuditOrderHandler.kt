package com.example.order.handler

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskHandler
import org.camunda.bpm.client.task.ExternalTaskService
import org.springframework.stereotype.Component

/** Added together with the Audit step in OrderMain.bpmn. */
@Component
@ExternalTaskSubscription("auditOrder")
class AuditOrderHandler(
    private val auditLog: AuditLog
) : ExternalTaskHandler {

    override fun execute(task: ExternalTask, service: ExternalTaskService) {
        auditLog.record(task.getVariable("orderId"), task.getActivityId())
        service.complete(task)
    }
}
