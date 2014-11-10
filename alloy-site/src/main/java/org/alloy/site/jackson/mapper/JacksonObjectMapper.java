package org.alloy.site.jackson.mapper;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JacksonObjectMapper extends ObjectMapper {
	private static final long serialVersionUID = 8147977505402605048L;

	@PostConstruct
	public void initalize() {
		this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		this.findAndRegisterModules();
	}
}