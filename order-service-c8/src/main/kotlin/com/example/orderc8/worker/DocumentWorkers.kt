package com.example.orderc8.worker

import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.spring.client.annotation.JobWorker
import io.camunda.zeebe.spring.client.annotation.Variable
import org.springframework.stereotype.Component

/** Two job types served from one class. */
@Component
class DocumentWorkers(
    private val printer: Printer
) {

    @JobWorker(type = "print-label")
    fun printLabel(@Variable orderId: String) {
        printer.label(orderId)
    }

    @JobWorker(type = "print-invoice")
    fun printInvoice(@Variable orderId: String) {
        printer.invoice(orderId)
    }
}
