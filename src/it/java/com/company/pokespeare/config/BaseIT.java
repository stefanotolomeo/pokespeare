package com.company.pokespeare.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = { ITContext.class })
// @ExtendWith({ExternalSystemConnectedExtension.class})
@ActiveProfiles("it") // to load the application-it.yml file
@JsonTest
//@PropertySource(value = "classpath:application-it.yml", factory = YamlPropertyLoaderFactory.class)
public abstract class BaseIT {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

}