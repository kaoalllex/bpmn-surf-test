package com.example.orderc8.worker

import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.spring.client.annotation.JobWorker
import io.camunda.zeebe.spring.client.annotation.Variable
import org.springframework.stereotype.Component

/** "find-items" is a prefix of "find-items-in-catalog". */
@Component
class FindItemsWorker(
    private val itemRepository: ItemRepository
) {

    @JobWorker(type = "find-items")
    fun findItems(@Variable orderId: String): Map<String, Any> {
        val items = itemRepository.findByOrder(orderId).filter { it.inStock }
        return mapOf("itemCount" to items.size)
    }
}
