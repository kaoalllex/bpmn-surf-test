package com.example.orderc8.worker

import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.spring.client.annotation.JobWorker
import io.camunda.zeebe.spring.client.annotation.Variable
import org.springframework.stereotype.Component

@Component
class CheckAddressWorker(
    private val addressService: AddressService
) {

    @JobWorker(type = "check-address")
    fun check(@Variable orderId: String): Map<String, Any> =
        mapOf("addressValid" to addressService.verify(orderId))
}
