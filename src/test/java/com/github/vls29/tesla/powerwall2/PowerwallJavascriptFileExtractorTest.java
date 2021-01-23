package com.github.vls29.tesla.powerwall2;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PowerwallJavascriptFileExtractorTest {

	@Test
	public void getJavascriptFilesFromPowerwallUi() throws URISyntaxException, IOException, InterruptedException {
		List<File> javascriptFiles = PowerwallJavascriptFileExtractor.getJavascriptFilesFromPowerwallUi();
		assertEquals(2, javascriptFiles.size());
	}
}
