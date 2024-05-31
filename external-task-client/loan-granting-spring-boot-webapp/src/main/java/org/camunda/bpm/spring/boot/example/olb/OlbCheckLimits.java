package org.camunda.bpm.spring.boot.example.olb;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.spring.boot.example.VariableNames;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Slf4j
public class OlbCheckLimits implements JavaDelegate {
    public final String[] RESULTS = new String[]{"SUCCESS", "ERROR"};
    private final Random random = new Random();

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String inqReqId = (String) delegateExecution.getVariable(VariableNames.incomingRequestId.name());
        String validationResult = RESULTS[random.nextInt(2)];
        delegateExecution.setVariable(VariableNames.schemaValidate.name(), validationResult);

        log.info("Проверка достаточности лимитов inqReqId={} результат={}", inqReqId, validationResult);
    }
}
