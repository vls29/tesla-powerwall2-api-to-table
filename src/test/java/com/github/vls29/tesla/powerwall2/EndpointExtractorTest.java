package com.github.vls29.tesla.powerwall2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class EndpointExtractorTest {

	@Test
	public void extractPowerwall2ApiEndpointsFromSrc() throws IOException, URISyntaxException {

		Map<String, Set<HttpMethod>> endpoints = new HashMap<String, Set<HttpMethod>>();
		File jsFile = new File(this.getClass().getResource("/example.js").toURI());
		EndpointExtractor.extractEndpointsFromFile(endpoints, jsFile);
		assertEquals(59, endpoints.size());
	}
}
