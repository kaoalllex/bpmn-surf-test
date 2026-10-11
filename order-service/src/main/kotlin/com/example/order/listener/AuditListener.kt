package com.example.order.listener

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.ExecutionListener
import org.springframework.stereotype.Component

/** Referenced by camunda:class from a task (start and end) and from the process itself. */
@Component
class AuditListener : ExecutionListener {

    override fun notify(execution: DelegateExecution) {
        println("audit ${execution.eventName} ${execution.currentActivityId}")
    }
}
