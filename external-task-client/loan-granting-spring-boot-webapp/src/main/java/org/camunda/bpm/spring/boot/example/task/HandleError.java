package org.camunda.bpm.spring.boot.example.task;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.spring.boot.example.VariableNames;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HandleError implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("HandleError inReqId={}", delegateExecution.getVariable(VariableNames.incomingRequestId.name()));
    }
}
