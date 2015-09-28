package net.codesup.restbinder.client.http;

import java.io.InputStream;
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
	private final Map<String,HeaderField> headerFields;
	private final InputStream inputStream;
	private final HttpRequest request;

	HttpResponse(final HttpRequest request, final InputStream inputStream, final int statusCode, final String reasonPhrase, final Map<String, List<String>> headerFields) {
		this.request = request;
		this.statusCode = statusCode;
		this.reasonPhrase = reasonPhrase;
		final Map<String,HeaderField> headerFieldsBuilding = new LinkedHashMap<>();
		for(final Map.Entry<String,List<String>> entry : headerFields.entrySet()) {
			if(entry.getKey() != null) {
				headerFieldsBuilding.put(entry.getKey().toLowerCase(), HeaderField.newBuilder(entry.getKey()).addValue(entry.getValue()).build());
			}
		}
		this.headerFields = Collections.unmodifiableMap(headerFieldsBuilding);
		this.inputStream = inputStream;
	}

	public HttpRequest getRequest() {
		return this.request;
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	public String getReasonPhrase() {
		return this.reasonPhrase;
	}

	public HeaderField getHeader(final String key) {
		return this.headerFields.get(key.toLowerCase());
	}

	public MediaType getContentType() {
		final HeaderField contentTypeHeaderField = this.headerFields.get(HeaderField.CONTENT_TYPE.toLowerCase());
		return contentTypeHeaderField == null ? null : MediaType.valueOf(contentTypeHeaderField.getValue());
	}

	public long getContentLength() {
		final HeaderField contentLengthHeaderField = this.headerFields.get(HeaderField.CONTENT_LENGTH.toLowerCase());
		return contentLengthHeaderField == null ? -1 :  Long.parseLong(contentLengthHeaderField.getValue());
	}

	public boolean hasContent() {
		return getContentLength() > -1;
	}

	public HttpStatusCategory getStatus() {
		if(this.statusCode >= 100 && this.statusCode < 200) {
			return HttpStatusCategory.INFORMATIONAL;
		} else if(this.statusCode < 300) {
			return HttpStatusCategory.SUCCESS;
		} else if(this.statusCode < 400) {
			return HttpStatusCategory.REDIRECTION;
		} else if(this.statusCode < 500){
			return HttpStatusCategory.CLIENT_ERROR;
		} else if(this.statusCode < 600) {
			return HttpStatusCategory.SERVER_ERROR;
		} else {
			return HttpStatusCategory.UNKNOWN;
		}
	}

	public InputStream getInputStream() {
		return this.inputStream;
	}
}
