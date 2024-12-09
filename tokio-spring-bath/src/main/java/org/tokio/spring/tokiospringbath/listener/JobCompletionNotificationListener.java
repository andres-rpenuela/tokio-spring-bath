package org.tokio.spring.tokiospringbath.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JobCompletionNotificationListener implements JobExecutionListener {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void afterJob(JobExecution jobExecution) {
        if( jobExecution.getStatus() == BatchStatus.COMPLETED ){
            log.info("Job completed");
            jdbcTemplate.query("SELECT COUNT(*) as count FROM product",
                    (rs, row) -> rs.getLong("count") )
                    .forEach(count -> log.info("Now, there are " + count + " products in the database."));
        }
    }
}
