package com.github.vls29.tesla.powerwall2;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class ReadMe {

	private static final String LINE_FORMAT = "| %s | %s |  |  |  |\n";

	public static void generateReadmeEndpointsTable() throws IOException, URISyntaxException, InterruptedException {
		List<File> jsFiles = PowerwallJavascriptFileExtractor.getJavascriptFilesFromPowerwallUi();

		Map<String, Set<HttpMethod>> endpoints = new HashMap<String, Set<HttpMethod>>();
		for (File jsFile : jsFiles) {
			EndpointExtractor.extractEndpointsFromFile(endpoints, jsFile);
		}
		int longestEndpoint = getLongestEndpoint(endpoints).length();

		final StringBuilder readmeEndpointTable = new StringBuilder();
		endpoints.keySet().stream().sorted().forEach(x -> {
			formatEndointRow(readmeEndpointTable, x, endpoints.get(x), longestEndpoint);
		});

		System.out.println(readmeEndpointTable.toString());
	}

	private static String getLongestEndpoint(Map<String, Set<HttpMethod>> endpoints) {
		String longestEndpoint = "";
		for (String endpoint : endpoints.keySet()) {
			if (longestEndpoint.length() < endpoint.length()) {
				longestEndpoint = endpoint;
			}
		}

		return longestEndpoint;
	}

	private static void formatEndointRow(StringBuilder readmeEndpointTable, String endpoint, Set<HttpMethod> methods,
			int longestEndpoint) {
		List<HttpMethod> sortedMethods = methods.stream().sorted().collect(Collectors.toList());
		for (int i = 0; i < sortedMethods.size(); i++) {
			String col1 = "";
			if (i == 0) {
				col1 = StringUtils.rightPad("`" + endpoint + "`", (longestEndpoint + 2));
			} else {
				col1 = StringUtils.rightPad("", (longestEndpoint + 2));
			}

			String col2 = StringUtils.rightPad(sortedMethods.get(i).toString(), HttpMethod.getMaxMethodLength());

			readmeEndpointTable.append(String.format(LINE_FORMAT, col1, col2));
		}
	}
}
