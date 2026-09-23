package com.example.billing.messaging;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DunningKafkaListener {

    private static final String DUNNING_RESOLVED = "DunningResolved";

    private final RuntimeService runtimeService;

    public DunningKafkaListener(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    /** Case C: the constant is declared and used in the same file. */
    @KafkaListener(topics = "billing.dunning")
    public void onResolved(DunningEvent event) {
        runtimeService.createMessageCorrelation(DUNNING_RESOLVED)
                .processInstanceBusinessKey(event.getContractId())
                .correlate();
    }
}
