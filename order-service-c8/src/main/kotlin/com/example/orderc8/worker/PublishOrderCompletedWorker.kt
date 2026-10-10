package com.example.orderc8.worker

import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.spring.client.annotation.JobWorker
import io.camunda.zeebe.spring.client.annotation.Variable
import org.springframework.stereotype.Component
import io.camunda.zeebe.client.ZeebeClient

@Component
class PublishOrderCompletedWorker(
    private val zeebeClient: ZeebeClient
) {

    /** The message throw event is itself a job: the worker publishes the message. */
    @JobWorker(type = "publish-order-completed")
    fun publish(@Variable orderId: String) {
        zeebeClient.newPublishMessageCommand()
            .messageName("OrderCompletedC8")
            .correlationKey(orderId)
            .send()
            .join()
    }
}
