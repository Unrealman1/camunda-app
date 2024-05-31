package org.camunda.bpm.spring.boot.example.task;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.spring.boot.example.VariableNames;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Configuration
@Slf4j
public class brokerOrderHandlerConfig {

    @ExternalTaskSubscription("brokerOrderCreator")
    @Bean
    public ExternalTaskHandler brokerOrderCreator() {
        return (externalTask, externalTaskService) -> {
            String incReqId = externalTask.getVariable(VariableNames.incomingRequestId.name());
            UUID brokerOrderId = UUID.randomUUID();
            log.info("Создание НПБ reqId={} brokerOrderId={}", incReqId, brokerOrderId);
            externalTaskService.complete(externalTask, Variables.putValue(VariableNames.brokerOrderId.name(), brokerOrderId));
        };
    }
}
