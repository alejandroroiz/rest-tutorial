package com.base22.rest.tutorial.configuration;

import com.base22.rest.tutorial.util.ApplicationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

  private static final Logger logger = LogManager.getLogger();

  @Bean
  public DataSource getDataSource() {

    logger.info("Starting initializing the database datasource");

    DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

    dataSourceBuilder.driverClassName(ApplicationUtils.getStringProperty("database.driver"));
    dataSourceBuilder.url(ApplicationUtils.getStringProperty("database.url"));
    dataSourceBuilder.username(ApplicationUtils.getStringProperty("database.username"));
    dataSourceBuilder.password(ApplicationUtils.getStringProperty("database.password"));

    logger.info("Successfully initialized the database datasource");

    return dataSourceBuilder.build();
  }
  
}
