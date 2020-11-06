package com.base22.rest.tutorial.util;

import com.base22.rest.tutorial.exception.AppConfigException;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.YAMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.util.HashMap;

public class AppConfigManager {

  private String fileName = "";
  private static HashMap<String, FileBasedConfigurationBuilder<FileBasedConfiguration>> builders = new HashMap();

  public AppConfigManager() {
  }

  private static void configure(String fileName) {
    Parameters params = new Parameters();
    File yamlFile = new File(fileName);
    FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
        (new FileBasedConfigurationBuilder(YAMLConfiguration.class))
            .configure(params.fileBased().setFile(yamlFile).setEncoding("UTF-8"));
    builders.put(fileName, builder);
  }

  public static FileBasedConfiguration getConfiguration(String pathToFile) throws AppConfigException {
    if (!builders.containsKey(pathToFile)) {
      configure(pathToFile);
    }

    try {
      return (FileBasedConfiguration) ((FileBasedConfigurationBuilder) builders.get(pathToFile)).getConfiguration();
    } catch (NullPointerException | ConfigurationException var2) {
      throw new AppConfigException(
          "APPLICATION CONFIGURATION ERROR: The Application Configuration could not be retrieved from file '"
              + pathToFile + "'\nUnderlying error: " + var2.getMessage(), var2);
    }
  }
}
