package org.tokio.spring.tokiospringbath.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.tokio.spring.tokiospringbath.domain.Product;
import org.tokio.spring.tokiospringbath.listener.JobCompletionNotificationListener;
import org.tokio.spring.tokiospringbath.processor.ProductItemProcessor;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
public class BathConfiguration {

    private static final String PRODUCT_ITEM_READER_NAME = "productItemReader";
    private static final String PRODUCT_CSV_READER_NAME = "products.csv";
    private static final String[] PRODUCT_CSV_FIELDS = new String[] { "name", "description", "category", "price", "discount", "taxes", "stock"};


    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager, JdbcTemplate jdbcTemplate,DataSource dataSource) throws Exception {
        return new JobBuilder("job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(new JobCompletionNotificationListener(jdbcTemplate) )
                .start(chunkStep(jobRepository, transactionManager,dataSource))
                .build();
    }

    @Bean
    public Step chunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,DataSource dataSource) {
        return new StepBuilder("step", jobRepository).<Product,Product>chunk(10,transactionManager)
                .reader(csvListReader())
                .processor(productProcessor())
                .writer(productWriter(dataSource))
                .build();
    }

    //@StepScope
    @Bean
    public FlatFileItemReader<Product> csvListReader() {
            return new FlatFileItemReaderBuilder<Product>()
                    .name(PRODUCT_ITEM_READER_NAME)
                    .resource(new ClassPathResource(PRODUCT_CSV_READER_NAME))
                    .delimited()
                    .names(PRODUCT_CSV_FIELDS)
                    .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                        setTargetType(Product.class);
                    }})
                    .build();
    }

    //@StepScope
    @Bean
    public ItemProcessor<Product, Product> productProcessor() {
        return new ProductItemProcessor();
    }

    //@StepScope
    @Bean
    public JdbcBatchItemWriter<Product> productWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Product>().itemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<Product>()
        ).sql("INSERT INTO product (name,description,category,price,discount,taxes,stock_quantity) VALUES (" +
                ":name, :description, :category, :price, :discount, :taxes, :stock)")
                .dataSource(dataSource).build();
    }
}
