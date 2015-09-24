package net.codesup.restbinder.client.http;

import java.net.HttpURLConnection;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Mirko Klemm 2015-09-22
 */
public class HttpRequest {
	private final boolean allowUserInteraction;
	private final boolean doInput;
	private final boolean doOutput;
	private final long ifModifiedSince;
	private final boolean useCaches;
	private final int chunkSize;
	private final long contentLength;
	private final int connectTimeout;
	private final int readTimeout;
	private final Map<String, Header> headers;
	private final String method;
	private final URI uri;

	private HttpRequest(final String method, final URI uri, final boolean allowUserInteraction, final boolean doInput, final boolean doOutput, final long ifModifiedSince, final boolean useCaches, final int chunkSize, final long contentLength, final int connectTimeout, final int readTimeout, final Map<String, Header> headers) {
		this.allowUserInteraction = allowUserInteraction;
		this.doInput = doInput;
		this.doOutput = doOutput;
		this.ifModifiedSince = ifModifiedSince;
		this.useCaches = useCaches;
		this.chunkSize = chunkSize;
		this.contentLength = contentLength;
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
		this.headers = headers;
		this.method = method;
		this.uri = uri;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public Map<String, Header> getHeaders() {
		return this.headers;
	}

	public String getMethod() {
		return this.method;
	}

	public URI getUri() {
		return this.uri;
	}

	public Builder newCopyBuilder() {
		return new Builder(this);
	}

	public boolean isAllowUserInteraction() {
		return this.allowUserInteraction;
	}

	public boolean isDoInput() {
		return this.doInput;
	}

	public boolean isDoOutput() {
		return this.doOutput;
	}

	public int getChunkSize() {
		return this.chunkSize;
	}

	public long getContentLength() {
		return this.contentLength;
	}

	public int getConnectTimeout() {
		return this.connectTimeout;
	}

	public int getReadTimeout() {
		return this.readTimeout;
	}

	public long getIfModifiedSince() {
		return this.ifModifiedSince;
	}

	public boolean isUseCaches() {
		return this.useCaches;
	}

	public static class Builder {
		private final Map<String, Header> headers;
		private boolean allowUserInteraction = HttpURLConnection.getDefaultAllowUserInteraction();
		private boolean doInput = true;
		private boolean doOutput = false;
		private long ifModifiedSince = 0;
		private boolean useCaches = true;
		private String method;
		private URI uri;
		private long contentLength;
		private int readTimeout;
		private int connectTimeout;
		private int chunkSize;

		private Builder() {
			this.headers = new LinkedHashMap<>();
		}

		private Builder(final HttpRequest other) {
			this.method = other.method;
			this.uri = other.uri;
			this.headers = new LinkedHashMap<>(other.getHeaders());
			this.allowUserInteraction = other.allowUserInteraction;
			this.doInput = other.doInput;
			this.doOutput = other.doOutput;
			this.ifModifiedSince = other.ifModifiedSince;
			this.useCaches = other.useCaches;
		}

		public Builder addHeader(final Header... newHeaders) {
			for (final Header header : newHeaders) {
				final Header existingHeader = this.headers.get(header.getKey());
				final Header newHeader = existingHeader == null ? new Header(header.getKey(), header.getValues()) : Header.newBuilder(header.getKey()).addValue(existingHeader.getValues()).addValue(header.getValues()).build();
				this.headers.put(header.getKey(), newHeader);
			}
			return this;
		}

		public Builder withAllowUserInteraction(final boolean newAllowUserInteraction) {
			this.allowUserInteraction = newAllowUserInteraction;
			return this;
		}

		public Builder withDoInput(final boolean newDoInput) {
			this.doInput = newDoInput;
			return this;
		}

		public Builder withDoOutput(final boolean newDoOutput) {
			this.doOutput = newDoOutput;
			return this;
		}

		public Builder withIfModifiedSince(final long newIfModifiedSince) {
			this.ifModifiedSince = newIfModifiedSince;
			return this;
		}

		public Builder withUseCaches(final boolean newUseCaches) {
			this.useCaches = newUseCaches;
			return this;
		}

		public Builder addHeader(final String key, final Object... values) {
			return addHeader(new Header(key, values));
		}

		public Builder withAccept(final Accept<MediaType>... accept) {
			return addHeader(Header.ACCEPT, accept);
		}

		public Builder withAccept(final MediaType... accept) {
			return addHeader(Header.ACCEPT, accept);
		}

		public Builder withAcceptEncoding(final Accept<ContentCoding>... accept) {
			return addHeader(Header.ACCEPT_ENCODING, accept);
		}

		public Builder withAcceptEncoding(final ContentCoding... accept) {
			return addHeader(Header.ACCEPT_ENCODING, accept);
		}

		public Builder withContentType(final MediaType contentType) {
			return addHeader(Header.CONTENT_TYPE, contentType);
		}

		public Builder withContentLength(final long newContentLength) {
			this.contentLength = newContentLength;
			return this;
		}

		public Builder withReadTimeout(final int newReadTimeout) {
			this.readTimeout = newReadTimeout;
			return this;
		}

		public Builder withConnectTimeout(final int newConnectTimeout) {
			this.connectTimeout = newConnectTimeout;
			return this;
		}

		public Builder withChunkSize(final int newChunkSize) {
			this.chunkSize = newChunkSize;
			return this;
		}

		public Builder withContentEncoding(final ContentCoding contentCoding) {
			return addHeader(Header.CONTENT_ENCODING, contentCoding);
		}

		public Builder withUri(final URI newUri) {
			this.uri = newUri;
			return this;
		}

		public Builder withMethod(final String newMethod) {
			this.method = newMethod;
			return this;
		}

		public HttpRequest build() {
			return new HttpRequest(this.method, this.uri, this.allowUserInteraction, this.doInput, this.doOutput, this.ifModifiedSince, this.useCaches, this.chunkSize, this.contentLength, this.connectTimeout, this.readTimeout, this.headers);
		}
	}
}
