package org.example.configuration;

import org.example.FileReader.SimFileReader;
import org.example.entity.Sim;
import org.example.job.SimFileMetadata;
import org.example.listener.SimStepListener;
import org.example.processor.SimRecordProcessor;
import org.example.repository.SimRepository;
import org.example.writer.SimWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfiguration {

    @Value("${sim.data.filepath}")
    private String simDataFilePath;

    @Bean
    public SimFileReader simFileReader(SimFileMetadata simFileMetadata) {
        return new SimFileReader(simDataFilePath+"/inputs/sim_data1.txt", simFileMetadata);
    }

    @Bean
    public ItemProcessor<Sim, Sim> processor(SimFileMetadata simFileMetadata) {
        return new SimRecordProcessor(simFileMetadata);
    }

    @Bean
    public SimWriter writer(SimRepository simRepository) {
        return new SimWriter(simRepository);
    }

    @Bean
    public Job job(Step step, JobRepository jobRepository) {
        return new JobBuilder("job", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step step(final JobRepository jobRepository,
                     final PlatformTransactionManager transactionManager,
                     SimFileReader reader,
                     ItemProcessor<Sim, Sim> processor,
                     SimWriter simWriter) {
        return new StepBuilder("step", jobRepository)
                .<Sim, Sim>chunk(100, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(simWriter)
                .listener(new SimStepListener(simDataFilePath+"/outputs/sim_data1"))
                .build();
    }
}
