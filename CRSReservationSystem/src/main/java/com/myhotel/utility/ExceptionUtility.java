package com.myhotel.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ExceptionUtility {

	Logger logger = LoggerFactory.getLogger(ExceptionUtility.class);

	public String notFoundCause(String type, Long id) {
		logger.error("{} with id: {} not found", type, id);
		return type + " with id: " + id + " not found";
	}

	public String notFoundMessage(String type) {
		logger.error("{} not found", type);
		return type + " not found";
	}
}
