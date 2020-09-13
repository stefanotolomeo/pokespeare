package com.company.pokespeare.testconfig;

import com.company.pokespeare.config.WebConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = { TestContext.class, WebConfig.class })
@ExtendWith(SpringExtension.class)
@WebMvcTest
@ActiveProfiles("unit")
public abstract class BaseWebTest {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

}
