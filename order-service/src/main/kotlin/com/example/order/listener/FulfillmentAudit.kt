package com.example.order.listener

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.ExecutionListener
import org.springframework.stereotype.Component

/** A process-level listener of the pool-less Fulfillment process. */
@Component
class FulfillmentAudit : ExecutionListener {

    override fun notify(execution: DelegateExecution) {
        println("fulfillment ${execution.eventName} ${execution.processInstanceId}")
    }
}
