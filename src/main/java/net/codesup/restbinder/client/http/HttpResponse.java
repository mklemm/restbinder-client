package net.codesup.restbinder.client.http;

import java.io.InputStream;
import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mirko Klemm 2015-09-22
 */
public class HttpResponse {
	private final int statusCode;
	private final String reasonPhrase;
	private final Map<String,Header> headerFields;
	private final InputStream inputStream;
	private final URI uri;

	HttpResponse(final URI uri, final InputStream inputStream, final int statusCode, final String reasonPhrase, final Map<String, List<String>> headerFields) {
		this.uri = uri;
		this.statusCode = statusCode;
		this.reasonPhrase = reasonPhrase;
		final Map<String,Header> headerFieldsBuilding = new LinkedHashMap<>();
		for(final Map.Entry<String,List<String>> entry : headerFields.entrySet()) {
			headerFieldsBuilding.put(entry.getKey(), new Header(entry.getKey(), entry.getValue()));
		}
		this.headerFields = Collections.unmodifiableMap(headerFieldsBuilding);
		this.inputStream = inputStream;
	}

	public URI getUri() {
		return this.uri;
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	public String getReasonPhrase() {
		return this.reasonPhrase;
	}

	public Map<String, Header> getHeaderFields() {
		return this.headerFields;
	}

	public MediaType getContentType() {
		return MediaType.valueOf(this.headerFields.get(Header.CONTENT_TYPE).getValue());
	}

	public InputStream getInputStream() {
		return this.inputStream;
	}
}
