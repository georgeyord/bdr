package com.webwonder.atm.integrationtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AtmApplicationShould {
	@Autowired
	WebApplicationContext webApplicationContext;

	@Test
	public void loadApplicationContext() {
		assertThat(webApplicationContext).isNotNull();
	}
}
