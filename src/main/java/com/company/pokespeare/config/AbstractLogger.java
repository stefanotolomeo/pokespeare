package com.company.pokespeare.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractLogger {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
}
