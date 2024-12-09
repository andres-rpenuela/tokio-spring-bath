package org.tokio.spring.springbathsimple.bath;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableAutoConfiguration
public class BathConfiguration {

    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(chunkStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step chunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step", jobRepository).<String,String>chunk(10,transactionManager)
                .reader(stringListReader())
                .processor(stringProcessor())
                .writer(stringWriter())
                .build();
    }

    //@StepScope
    @Bean
    public ListItemReader<String> stringListReader() {
        List<String> data = Arrays.asList("Spring", "Boot", "Batch", "Example", "Processing", "Strings");
        return new ListItemReader<>(data);
    }

    //@StepScope
    @Bean
    public ItemProcessor<String, String> stringProcessor() {
        return item -> item.toUpperCase(); // Transforma o texto em maiúsculo
    }

    //@StepScope
    @Bean
    public ItemWriter<String> stringWriter() {
        return items -> {
            System.out.println("Itens processados:");
            items.forEach(System.out::println);
        };
    }
}
