package net.codesup.restbinder.client;

import java.net.URI;
import java.util.List;

import net.codesup.restbinder.client.http.JaxbMediaType;

/**
 * @author Mirko Klemm 2015-09-23
 */
public class DocumentDescriptor<T> {
	private final ClientSession clientSession;
	private final JaxbMediaType<T> contentType;
	private final List<LinkDescriptor<T, ?>> linkDescriptors;
	private final long maxAgeInCache;

	public DocumentDescriptor(final ClientSession clientSession, final JaxbMediaType<T> contentType, final List<LinkDescriptor<T,?>> linkDescriptors, final long maxAgeInCache) {
		this.clientSession = clientSession;
		this.contentType = contentType;
		this.linkDescriptors = linkDescriptors;
		this.maxAgeInCache = maxAgeInCache;
	}

	public DocumentDescriptor(final ClientSession clientSession, final JaxbMediaType<T> contentType, final List<LinkDescriptor<T,?>> linkDescriptors) {
		this.clientSession = clientSession;
		this.contentType = contentType;
		this.linkDescriptors = linkDescriptors;
		this.maxAgeInCache = clientSession.getMaxAgeInCache();
	}

	public ClientSession getClientSession() {
		return this.clientSession;
	}

	public JaxbMediaType<T> getContentType() {
		return this.contentType;
	}

	public List<LinkDescriptor<T,?>> getLinkDescriptors() {
		return this.linkDescriptors;
	}

	public WebDocument<T> createDocument(final URI uri, final T content) {
		return new WebDocument<>(this, uri, content);
	}

	public long getMaxAgeInCache() {
		return this.maxAgeInCache;
	}
}
