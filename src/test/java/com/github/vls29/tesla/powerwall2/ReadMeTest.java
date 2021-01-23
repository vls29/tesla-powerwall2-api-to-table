package com.github.vls29.tesla.powerwall2;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

public class ReadMeTest {

	@Test
	public void generateReadmeEndpointsTable() throws IOException, URISyntaxException, InterruptedException {
		ReadMe.generateReadmeEndpointsTable();
	}
}
