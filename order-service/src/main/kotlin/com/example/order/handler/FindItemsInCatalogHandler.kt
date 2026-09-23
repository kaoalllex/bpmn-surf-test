package com.example.order.handler

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskHandler
import org.camunda.bpm.client.task.ExternalTaskService
import org.springframework.stereotype.Component

/**
 * Deliberately named so that the topic of FindItemsHandler is a prefix of this
 * one — a blob search for "findItems" matches both files (BUG-0027).
 */
@Component
@ExternalTaskSubscription("findItemsInCatalog")
class FindItemsInCatalogHandler(
    private val catalogClient: CatalogClient
) : ExternalTaskHandler {

    override fun execute(task: ExternalTask, service: ExternalTaskService) {
        val found = catalogClient.lookup(task.getVariable("itemCodes"))
        service.complete(task, mapOf("catalogItems" to found))
    }
}
