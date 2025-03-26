package br.com.alura.codetickets;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * classe específica para manter as configurações necessárias no banco de dados.
 */
@Configuration
public class DataSourceConfig {

    /**
     *
     * @return DataSource
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource") //prefixo de configuracao do banco que esta no application properties
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }


    /**
     * Assim, o controle de transação transactionManager opera e controla transações no @Qualifier que definimos como dataSource.
     * @Qualifier para indicar que o qualifier é o metodo dataSource.
     * @param dataSource
     * @return PlatformTransactionManager
     */
    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }



}
