package com.example.orderc8.worker

import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.spring.client.annotation.JobWorker
import io.camunda.zeebe.spring.client.annotation.Variable
import org.springframework.stereotype.Component

/** The annotation spans several lines. */
@Component
class FindItemsInCatalogWorker(
    private val catalog: CatalogClient
) {

    @JobWorker(
        type = "find-items-in-catalog",
        timeout = 60_000
    )
    fun findInCatalog(@Variable orderId: String): Map<String, Any> =
        mapOf("catalogItems" to catalog.lookup(orderId))
}
