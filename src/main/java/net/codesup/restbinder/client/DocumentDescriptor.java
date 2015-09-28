package net.codesup.restbinder.client;

import java.net.URI;
import java.util.List;

import net.codesup.restbinder.client.http.HttpResponse;
import net.codesup.restbinder.client.http.MediaType;

/**
 * @author Mirko Klemm 2015-09-23
 */
public class DocumentDescriptor<T> {
	private final ClientSession clientSession;
	private final MediaType contentType;
	private final DocumentMarshaller marshaller;
	private final List<LinkDescriptor<T>> linkDescriptors;
	private final long maxAgeInCache;

	public DocumentDescriptor(final ClientSession clientSession, final MediaType contentType, final List<LinkDescriptor<T>> linkDescriptors, final DocumentMarshaller marshaller, final long maxAgeInCache) {
		this.clientSession = clientSession;
		this.contentType = contentType;
		this.linkDescriptors = linkDescriptors;
		this.marshaller = marshaller;
		this.maxAgeInCache = maxAgeInCache;
	}

	public DocumentDescriptor(final ClientSession clientSession, final MediaType contentType, final List<LinkDescriptor<T>> linkDescriptors, final DocumentMarshaller marshaller) {
		this.clientSession = clientSession;
		this.contentType = contentType;
		this.linkDescriptors = linkDescriptors;
		this.marshaller = marshaller;
		this.maxAgeInCache = clientSession.getMaxAgeInCache();
	}

	public ClientSession getClientSession() {
		return this.clientSession;
	}

	public MediaType getContentType() {
		return this.contentType;
	}

	public List<LinkDescriptor<T>> getLinkDescriptors() {
		return this.linkDescriptors;
	}

	public WebDocument<T> createDocument(final URI uri, final T content) {
		return new WebDocument<>(this, uri, content);
	}

	public long getMaxAgeInCache() {
		return this.maxAgeInCache;
	}

	public DocumentMarshaller getMarshaller() {
		return this.marshaller;
	}

	public WebDocument<T> unmarshal(final HttpResponse httpResponse) {
		return this.marshaller.unmarshal(this, httpResponse);
	}
}
