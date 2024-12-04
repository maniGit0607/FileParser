package org.example.listener;

import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.ExitStatus;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SimStepListener extends StepExecutionListenerSupport {

    private final String outputFileBasePath;

    public SimStepListener(String outputFileBasePath) {
        this.outputFileBasePath = outputFileBasePath;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        boolean hasErrors = stepExecution.getFailureExceptions().size() > 0 || stepExecution.getWriteCount() == 0;

        String suffix = hasErrors ? ".nok" : ".ok";
        String fileName = outputFileBasePath + suffix;

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(hasErrors ? "Validation or write failure occurred." : "All records processed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.afterStep(stepExecution);
    }
}
