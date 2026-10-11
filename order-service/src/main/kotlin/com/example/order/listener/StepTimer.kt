package com.example.order.listener

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.ExecutionListener
import org.springframework.stereotype.Component

/** Referenced as ${stepTimer} (camunda:delegateExpression) from a sequence flow. */
@Component("stepTimer")
class StepTimer : ExecutionListener {

    override fun notify(execution: DelegateExecution) {
        execution.setVariable("stepTakenAt", System.currentTimeMillis())
    }
}
