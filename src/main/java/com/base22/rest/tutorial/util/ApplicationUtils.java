package com.base22.rest.tutorial.util;

import com.base22.rest.tutorial.exception.AppConfigException;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

public class ApplicationUtils {

	private static String configurationFile = "";
	private static final String ENVIRONMENT_JVM_PROPERTY = "REST_TUTORIAL_ENVIRONMENT";
	private static final Logger logger = LogManager.getLogger();

	public static String getConfigurationFile() {

		if (configurationFile.isEmpty()) {
			setupConfigurationFile();
		}
		return configurationFile;
	}

	public static void setupConfigurationFile() {
		String environment = System.getenv(ENVIRONMENT_JVM_PROPERTY);

		logger.info("[Rest Tutorial] Running on environment {}", environment);
		System.out.println("[Rest Tutorial] Running on environment " + environment);

		if (environment == null) {
			logger.error(
				"Environment property '{}' is not defined in this server. The environment property is required for Rest Tutorial.",
				ENVIRONMENT_JVM_PROPERTY);
			System.out.println("Environment property '" + ENVIRONMENT_JVM_PROPERTY
				+ "' is not defined in this server. The environment property is required for Rest Tutorial.");
			configurationFile = "";
			return;
		}

		if (environment.startsWith("PRD")) {
			configurationFile = "rest-tutorial-prd.yml";
		} else if (environment.startsWith("STG")) {
			configurationFile = "rest-tutorial-stg.yml";
		} else if (environment.startsWith("INT")) {
			configurationFile = "rest-tutorial-int.yml";
		} else if (environment.startsWith("LOCAL")) {
			configurationFile = "rest-tutorial-local.yml";
		} else {
			logger.error("Environment property '{}' is not recognized, should start with PRD, STG, INT or LOCAL",
				ENVIRONMENT_JVM_PROPERTY);
			System.out.println("Environment property '" + ENVIRONMENT_JVM_PROPERTY
				+ "' is not recognized, should start with PRD, STG, INT or LOCAL'");
			configurationFile = "";
			return;
		}

		try {
			configurationFile = ResourceUtils.getFile("classpath:" + configurationFile).getAbsolutePath();
		} catch (FileNotFoundException e) {
			logger.error("Could not get the path to the file: '{}'", configurationFile);
			System.out.println("Could not get the path to the file: '" + configurationFile + "'");
			e.printStackTrace();
		}

		logger.info("[Rest Tutorial] Using file configuration: {}", configurationFile);
		System.out.println("[Rest Tutorial] Using file configuration: " + configurationFile);
	}

	public static String getStringProperty(String prop) {
		FileBasedConfiguration config = null;
		String value = null;
		try {
			config = AppConfigManager.getConfiguration(getConfigurationFile());
			value = config.getString(prop);
		} catch (AppConfigException e) {
			logger.error("AppConfigException: ", e);
		}
		return value;
	}
}
