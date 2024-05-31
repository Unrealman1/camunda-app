package org.camunda.bpm.spring.boot.example.task;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.spring.boot.example.VariableNames;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
@Slf4j
public class ValidateIncReq implements JavaDelegate {
    public final String[] RESULTS = new String[]{"SUCCESS", "ERROR"};
    private final Random random = new Random();

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution.setVariable(VariableNames.incomingRequestId.name(), UUID.randomUUID().toString());
        String inqReqId = (String) delegateExecution.getVariable(VariableNames.incomingRequestId.name());
        String validationResult = RESULTS[random.nextInt(10) > 0 ? 0 : 1];
        delegateExecution.setVariable(VariableNames.schemaValidate.name(), validationResult);

        log.info("Валидация входящей команды inqReqId={} результат={}", inqReqId, validationResult);
    }
}
