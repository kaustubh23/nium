package com.nium.interview.transfers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransfersApplicationTests {

	@Test
	public void fileNotFoundException() {

		try {
	
			final URL file = getClass().getClassLoader().getResource("sample.txt");

			new TransfersApplication().process(file);
		} catch (Exception expected) {
			String expectedMessage = "Cannot invoke \"java.net.URL.toURI()\" because \"file\" is null";
			String actualMessage = expected.getMessage();

			assertTrue(actualMessage.contains(expectedMessage));
		}

	}

	@Test
	public void fileSuccessfulScenario() {


		final URL file = getClass().getClassLoader().getResource("transfers.txt");
		String success = new TransfersApplication().process(file);
		
		assertTrue(success=="Process Successful");
	}
	
	@Test
	public void fileFailedScenario() {


		final URL file = getClass().getClassLoader().getResource("transfers2.txt");
		String success = new TransfersApplication().process(file);
		
		assertTrue(success=="Process failed");
	}
	
	@Test
	public void testSuccessScenario() {
		final URL file = getClass().getClassLoader().getResource("another.txt");
		String success = new TransfersApplication().process(file);
		assertTrue(success=="Process Successful");
	}

}
