package hello;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    // tag::readerwriterprocessor[]
    @Bean
    public ItemReader<Score> reader() {
        FlatFileItemReader<Score> reader = new FlatFileItemReader<Score>();
        reader.setResource(new ClassPathResource("scores.csv"));
        reader.setLineMapper(new DefaultLineMapper<Score>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] {"courseID","studentID","score" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Score>() {{
                setTargetType(Score.class);
            }});
        }});
        return reader;
    }

    @Bean
    public ItemProcessor<Score, Grade> processor() {
        return new ScoreItemProcessor();
    }

    @Bean
    public ItemWriter<Grade> writer(DataSource dataSource) {
        JdbcBatchItemWriter<Grade> writer = new JdbcBatchItemWriter<Grade>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Grade>());
        writer.setSql("INSERT INTO grades (student_id, course_id, score, grade) VALUES (:studentID, :courseID, :score, :grade)");
        writer.setDataSource(dataSource);
        return writer;
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
    public Job generateGrade(JobBuilderFactory jobs, Step s1, JobExecutionListener listener) {
        return jobs.get("generateGrade")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(s1)
                .end()
                .build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Score> reader,
            ItemWriter<Grade> writer, ItemProcessor<Score, Grade> processor) {
        return stepBuilderFactory.get("step1")
                .<Score, Grade> chunk(5)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
    // end::jobstep[]

    @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;
    }

}
