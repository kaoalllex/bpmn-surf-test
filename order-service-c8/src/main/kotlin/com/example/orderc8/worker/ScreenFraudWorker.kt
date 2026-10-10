package com.example.orderc8.worker

import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.spring.client.annotation.JobWorker
import io.camunda.zeebe.spring.client.annotation.Variable
import org.springframework.stereotype.Component

/** The job type is the method name. */
@Component
class ScreenFraudWorker(
    private val fraudService: FraudService
) {

    // No type: the job type defaults to the method name, "screenFraud".
    @JobWorker
    fun screenFraud(@Variable orderId: String): Map<String, Any> =
        mapOf("fraudScore" to fraudService.score(orderId))
}
