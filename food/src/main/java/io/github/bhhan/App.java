package io.github.bhhan;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster(TaskExecutor asyncEventTaskExecutor) {
        SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();
        multicaster.setTaskExecutor(asyncEventTaskExecutor);
        return multicaster;
    }

    @Bean
    public TaskExecutor asyncEventTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setMaxPoolSize(5);
        threadPoolTaskExecutor.setThreadNamePrefix("eventExecutor-");
        threadPoolTaskExecutor.afterPropertiesSet();
        return threadPoolTaskExecutor;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory queryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
