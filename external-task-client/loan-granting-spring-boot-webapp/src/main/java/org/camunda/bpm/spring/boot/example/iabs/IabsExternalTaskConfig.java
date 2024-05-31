package org.camunda.bpm.spring.boot.example.iabs;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.variable.Variables;
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
public class IabsExternalTaskConfig {
    public final String[] RESULTS = new String[]{"SUCCESS", "ERROR"};


    @ExternalTaskSubscription("iabsSecurityIncrease")
    @Bean
    public ExternalTaskHandler iabsSecurityIncrease() {
        return (externalTask, externalTaskService) -> {
            String incReqId = externalTask.getVariable(VariableNames.incomingRequestId.name());
            UUID brokerOrderId = externalTask.getVariable(VariableNames.brokerOrderId.name());
            log.info("Увеличение ЦБ в 2112 reqId={} brokerOrderId={}", incReqId, brokerOrderId);


            //TODO бросам переодически исключение чтоб показать обработку ошибок
            int nextInt = new Random().nextInt(5);
            if (nextInt == 0) {
                externalTaskService.handleBpmnError(externalTask, "2112_increase_fail");
            } else {
                externalTaskService.complete(externalTask, Map.of(VariableNames.iabsStatus.name(), nextInt > 3 ? RESULTS[1] : RESULTS[0]));
            }
        };
    }

    @ExternalTaskSubscription("iabsSecurityDecrease")
    @Bean
    public ExternalTaskHandler iabsSecurityDecrease() {
        return (externalTask, externalTaskService) -> {
            String incReqId = externalTask.getVariable(VariableNames.incomingRequestId.name());
            UUID brokerOrderId = externalTask.getVariable(VariableNames.brokerOrderId.name());
            log.info("Уменьшение ЦБ в 2112 reqId={} brokerOrderId={}", incReqId, brokerOrderId);
            externalTaskService.complete(externalTask);
        };
    }
}
