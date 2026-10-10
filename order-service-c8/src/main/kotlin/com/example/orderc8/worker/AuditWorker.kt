package com.example.orderc8.worker

import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.spring.client.annotation.JobWorker
import io.camunda.zeebe.spring.client.annotation.Variable
import org.springframework.stereotype.Component

@Component
class AuditWorker(
    private val auditLog: AuditLog
) {

    /** Serves the execution listener on "Validate the order", not a task. */
    @JobWorker(type = "audit-step")
    fun audit(job: ActivatedJob) {
        auditLog.record(job.processInstanceKey, job.elementId)
    }
}
