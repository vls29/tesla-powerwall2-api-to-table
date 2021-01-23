package com.github.vls29.tesla.powerwall2;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class PowerwallJavascriptFileExtractor {

	private static final String SEARCH_STRING_1 = "src=\"/([\\w|\\.]+)\\.js\"";
	
	private static final String TESLA_POWERWALL_ADDRESS = "<replace with your powerwall address, e.g. https://powerwall/>";
	
	
	public static List<File> getJavascriptFilesFromPowerwallUi() throws URISyntaxException, IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder().uri(new URI(TESLA_POWERWALL_ADDRESS)).GET().build();

		HttpResponse<String> response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
		return extractJavascriptFiles(response.body());
	}
	
	private static List<File> extractJavascriptFiles(String response) throws IOException, InterruptedException, URISyntaxException {
		Pattern pattern1 = Pattern.compile(SEARCH_STRING_1);
		Matcher matcher = pattern1.matcher(response);
		
		List<File> javascriptFiles = new ArrayList<File>();
		
		while (matcher.find()) {
			String fileName = matcher.group(1);
			File file = File.createTempFile(fileName, ".js");
			HttpRequest request = HttpRequest.newBuilder().uri(new URI(TESLA_POWERWALL_ADDRESS + "/" + fileName + ".js")).GET().build();
			HttpResponse<String> jsFileResponse = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
			FileUtils.write(file, jsFileResponse.body(), Charset.defaultCharset());
			javascriptFiles.add(file);
		}
		
		return javascriptFiles;
	}
}
