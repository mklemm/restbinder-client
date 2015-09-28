package net.codesup.restbinder.client;

import net.codesup.restbinder.client.http.HttpResponse;

/**
 * @author Mirko Klemm 2015-09-25
 */
public class DocumentResponseException extends RuntimeException {
	final HttpResponse httpResponse;

	public DocumentResponseException(final HttpResponse httpResponse) {
		this(httpResponse.getRequest().getMethod() + " " + httpResponse.getRequest().getUri() + " --> " + httpResponse.getStatusCode() + " " + httpResponse.getReasonPhrase(), httpResponse);
	}

	public DocumentResponseException(final String message, final HttpResponse httpResponse) {
		super(message);
		this.httpResponse = httpResponse;
	}

	public DocumentResponseException(final String message, final Throwable cause, final HttpResponse httpResponse) {
		super(message, cause);
		this.httpResponse = httpResponse;
	}

	public DocumentResponseException(final Throwable cause, final HttpResponse httpResponse) {
		super(cause);
		this.httpResponse = httpResponse;
	}

	public DocumentResponseException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace, final HttpResponse httpResponse) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.httpResponse = httpResponse;
	}

	public HttpResponse getHttpResponse() {
		return this.httpResponse;
	}
}
