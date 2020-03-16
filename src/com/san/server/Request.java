package com.san.server;

import java.util.Map;

public class Request {
	private String method;
	private String path;
	private Map<String, String> headers;
	private Map<String, String> queryParams;
	private String body;
	
	public Request(String method, String path, Map<String, String> headers, Map<String, String> queryParams, String body) {
		this.method = method;
		this.path = path;
		this.headers = headers;
		this.queryParams = queryParams;
		this.body = body;
	}

	public String getMethod() {
		return method;
	}

	public String getPath() {
		return path;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public Map<String, String> getQueryParams() {
		return queryParams;
	}

	public String getBody() {
		return body;
	}
	
	@Override
	public String toString() {
		String result = "method: "+ method+" path: "+path;
		return result;
	}
}
