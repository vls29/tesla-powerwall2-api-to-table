package com.github.vls29.tesla.powerwall2;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class EndpointExtractor {
	private static final String SEARCH_STRING_1 = "\\.api\\.uri\\+\"(/[\\w|/|_|$|\\{|\\}|\\.]+)\"(?:,\\{method:\"(GET|POST|PUT|DELETE|PATCH|OPTIONS|HEAD)\")?";
	private static final String SEARCH_STRING_2 = "\\.api\\.uri\\}(/[\\w|/|_|\\$|\\{|\\}|\\.]+)\\`(?:,\\{method:\"(GET|POST|PUT|DELETE|PATCH|OPTIONS|HEAD)\")?";

	public static void extractEndpointsFromFile(Map<String, Set<HttpMethod>> endpoints,File file) throws IOException {
		String fileContent = FileUtils.readFileToString(file, Charset.defaultCharset());

		extractEndpointsForParticularRegex(endpoints, SEARCH_STRING_1, fileContent);
		extractEndpointsForParticularRegex(endpoints, SEARCH_STRING_2, fileContent);
	}

	private static void extractEndpointsForParticularRegex(Map<String, Set<HttpMethod>> endpoints, String regex,
			String fileContent) {
		Pattern pattern1 = Pattern.compile(regex);
		Matcher matcher = pattern1.matcher(fileContent);
		while (matcher.find()) {
			String endpoint = matcher.group(1);
			if (matcher.groupCount() == 1) {
				addEndpointToEndpoints(endpoints, endpoint, HttpMethod.GET);
			} else {
				addEndpointToEndpoints(endpoints, endpoint, HttpMethod.instance(matcher.group(2)));
			}
		}
	}

	private static void addEndpointToEndpoints(Map<String, Set<HttpMethod>> endpoints, String endpoint,
			HttpMethod method) {
		Set<HttpMethod> endpointMethods = endpoints.getOrDefault(endpoint, new HashSet<HttpMethod>());
		endpointMethods.add(method);
		endpoints.put(endpoint, endpointMethods);
	}
}
