package org.camunda.bpm.spring.boot.example.cds;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.spring.boot.example.HandlerConfiguration;
import org.camunda.bpm.spring.boot.example.VariableNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Configuration
@Slf4j
public class CdsExternalTaskConfig {

    @ExternalTaskSubscription("cdsOrderCreator")
    @Bean
    public ExternalTaskHandler cdsOrderCreator() {
        return (externalTask, externalTaskService) -> {
            String incReqId = externalTask.getVariable(VariableNames.incomingRequestId.name());
            UUID cdsOrderId = UUID.randomUUID();
            externalTaskService.setVariables(externalTask, Map.of(VariableNames.cdsOrderId.name(), cdsOrderId));
            log.info("CdsOrderCreator reqId={} подача поручения в ЦДС cdsOrderId={}", incReqId, cdsOrderId);
            externalTaskService.complete(externalTask);
        };
    }

    @ExternalTaskSubscription("cdsOrderCanceler")
    @Bean
    public ExternalTaskHandler cdsOrderCanceler() {
        return (externalTask, externalTaskService) -> {
            String incReqId = externalTask.getVariable(VariableNames.incomingRequestId.name());
            UUID cdsOrderId = externalTask.getVariable(VariableNames.cdsOrderId.name());

            log.info("cdsOrderCanceler reqId={} Отмена поручения в ЦДС cdsOrderId={}", incReqId, cdsOrderId);
            externalTaskService.complete(externalTask);
        };
    }
}
