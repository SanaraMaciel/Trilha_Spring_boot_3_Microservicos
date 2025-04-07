package br.com.alura.adopetstore.Configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Classe para configurações das threads em Spring boot
 */
@Configuration
public class AsyncConfiguration {

    @Bean
    public Executor asyncExecutor(){
        //configura as threads
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsynchThread-"); //informa o prefixo que vc quiser na sua thread
        executor.initialize();
        return executor;
    }



}
