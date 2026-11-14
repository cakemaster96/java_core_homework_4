package com.kotojava.lesson3;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class UserRepositoryConfig {

    @Bean
    public DataSource pgUserDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerNames(new String[]{""});
        dataSource.setPortNumbers(new int[]{8787});
        dataSource.setDatabaseName("javacore");
        dataSource.setUser("javacore");
        dataSource.setPassword("javacore_local");
        return dataSource;
    }

}
