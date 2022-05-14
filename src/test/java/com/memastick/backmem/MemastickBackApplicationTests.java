package com.memastick.backmem;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class MemastickBackApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void contextLoads() {
		String actual = applicationContext.getId();
		String expected = "memastick-test";

		Assert.assertEquals(expected, actual);
	}
}
