package com.masolria.config;

import com.masolria.util.YamlFactory;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


@Configuration
@PropertySource(value="classpath:application.yaml",factory = YamlFactory.class)
public class DataSourceConfig {
    /**
     * The Url of the connection.
     */
    @Value("${datasource.url}")
    private  String url;
    /**
     * The User of the connection.
     */
    @Value("${datasource.user}")
    private  String user;
    /**
     * The Password of the connection.
     */
    @Value("${datasource.password}")
    private  String password;
    @Value("${datasource.driver}")
    private  String driver;
//    @Value("${liquibase.enabled}")
//    private  String enabled;
    @Value("${liquibase.changelogFile}")
    private  String changeLog;
    @Value("${liquibase.schema}")
    private  String schema;
    @Bean
    public DriverManagerDataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(user);
        dataSource.setUrl(url);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        createMigrationSchema(dataSource);
        return dataSource;
    }
    @Bean
    SpringLiquibase liquibase(){
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setLiquibaseSchema(schema);
        liquibase.setChangeLog(changeLog);
        liquibase.setDataSource(dataSource());
        return liquibase;
    }
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    private void createMigrationSchema(DriverManagerDataSource dataSource){
        try(Connection conn = dataSource.getConnection()) {
          Statement prep =  conn.createStatement();

                   prep.execute("CREATE SCHEMA IF NOT EXISTS "+schema);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}