package com.github.vls29.tesla.powerwall2;

public enum HttpMethod {
	GET, POST, PUT, DELETE;

	public static HttpMethod instance(String method) {
		if (method == null) {
			return GET;
		}

		try {
			return valueOf(method);
		} catch (IllegalArgumentException e) {
			System.out.println("Couldn't find method of name '" + method + "'");
			return GET;
		}
	}

	public static int getMaxMethodLength() {
		int maxMethodLength = 0;
		for (HttpMethod method : HttpMethod.values()) {
			if (method.toString().length() > maxMethodLength) {
				maxMethodLength = method.toString().length();
			}
		}
		
		return maxMethodLength;
	}
}
