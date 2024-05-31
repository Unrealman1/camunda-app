package org.camunda.bpm.spring.boot.example.olb;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpm.spring.boot.example.HandlerConfiguration;
import org.camunda.bpm.spring.boot.example.VariableNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Random;

@Configuration
@Slf4j
public class OlbExternalTaskConfig {

    @ExternalTaskSubscription("OlbBalanceLoader")
    @Bean
    public ExternalTaskHandler balanceLoader() {
        return (externalTask, externalTaskService) -> {
            String incReqId = externalTask.getVariable(VariableNames.incomingRequestId.name());
            int balance = new Random().nextInt(10);
            externalTaskService.setVariables(externalTask, Map.of(VariableNames.balance.name(), balance));
            log.info("CdsBalanceLoader reqId={} balance={}", incReqId, balance);

            ObjectValue value = Variables.objectValue(balance).create();

            // complete the external task
            externalTaskService.complete(externalTask, Variables.putValueTyped(VariableNames.balance.name(), value));
        };
    }
}
