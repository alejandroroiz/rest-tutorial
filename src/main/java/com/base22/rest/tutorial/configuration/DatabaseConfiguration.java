package com.base22.rest.tutorial.configuration;

import com.base22.rest.tutorial.exception.AppConfigException;
import com.base22.rest.tutorial.util.AppConfigManager;
import com.base22.rest.tutorial.util.ApplicationUtils;
import org.apache.commons.configuration2.FileBasedConfiguration;
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

    FileBasedConfiguration config;

    DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
    try {

      config = AppConfigManager.getConfiguration(ApplicationUtils.getConfigurationFile());
      dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
      dataSourceBuilder.url(config.getString("database.url"));
      dataSourceBuilder.username(config.getString("database.username"));
      dataSourceBuilder.password(config.getString("database.password"));
    } catch (AppConfigException e) {
      logger.error("AppConfigException: ", e);
      logger.error("There was an error configuring the database data source");
      return null;
    }

    return dataSourceBuilder.build();
  }

  /*@Bean
  public HibernateJpaAutoConfiguration getConfiguration() {
    
  }*/
}
