package com.company.pokespeare.itconfig;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(SpringExtension.class)
// @PropertySource(value = "classpath:application-it.yml")
@WebAppConfiguration
@ContextConfiguration(classes = { ITContext.class })
@TestPropertySource(locations = "classpath:application-it.yml")
@ActiveProfiles("it") // to load the application-it.yml file
public abstract class BaseIT {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

}