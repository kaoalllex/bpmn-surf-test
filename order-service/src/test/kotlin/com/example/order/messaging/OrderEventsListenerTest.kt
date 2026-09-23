package com.example.order.messaging

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/** A test hit for "OrderPaid" — the correlation panel hides these by default. */
class OrderEventsListenerTest {

    @Test
    fun `correlates the OrderPaid message`() {
        val correlation = recordCorrelation { listener.onPaymentReceived(paymentEvent()) }
        assertEquals("OrderPaid", correlation.messageName)
    }
}
