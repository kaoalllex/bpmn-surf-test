package com.example.orderc8.worker

import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.spring.client.annotation.JobWorker
import io.camunda.zeebe.spring.client.annotation.Variable
import org.springframework.stereotype.Component

/** One instance per item of the multi-instance "Pack the items". */
@Component
class PackItemWorker(
    private val packing: PackingService
) {

    // Arguments but no type: the job type is still the method name, "packItem".
    @JobWorker(autoComplete = true, maxJobsActive = 8)
    fun packItem(@Variable item: Map<String, Any>): Map<String, Any> =
        mapOf("package" to packing.pack(item))
}
