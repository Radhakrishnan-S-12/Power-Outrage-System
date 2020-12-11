package com.poweroutrage.incident.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BaseConfiguration {

	protected ObjectMapper objectMapper;

	public void setUp() {
		this.objectMapper = createObjectMapper();
	}

	public ObjectMapper createObjectMapper() {
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(javaTimeModule);
		return objectMapper;
	}

	public String readFromFile(String jsonPath) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(new File(jsonPath));
		return IOUtils.toString(fileInputStream, "UTF-8");
	}
}
