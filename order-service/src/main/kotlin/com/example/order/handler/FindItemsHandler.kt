package com.example.order.handler

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskHandler
import org.camunda.bpm.client.task.ExternalTaskService
import org.springframework.stereotype.Component

@Component
@ExternalTaskSubscription("findItems")
class FindItemsHandler(
    private val itemRepository: ItemRepository
) : ExternalTaskHandler {

    override fun execute(task: ExternalTask, service: ExternalTaskService) {
        val items = itemRepository.findByOrder(task.getVariable("orderId"))
        service.complete(task, mapOf("itemCount" to items.size))
    }
}
