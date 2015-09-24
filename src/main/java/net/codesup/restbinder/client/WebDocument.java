package net.codesup.restbinder.client;

import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by klemm0 on 2015-09-22.
 */
public class WebDocument<T> {
	private final DocumentDescriptor<T> documentDescriptor;
	private final URI uri;
	private final T content;
	private final Map<String,Link<T,?>> links;
	private final Date loadedAt;

	WebDocument(final DocumentDescriptor<T> documentDescriptor, final URI uri, final T content) {
		this.documentDescriptor = documentDescriptor;
		this.uri = uri;
		this.content = content;
		this.links = Collections.unmodifiableMap(parseLinks());
		this.loadedAt = new Date();
	}

	public URI getUri() {
		return this.uri;
	}

	public T getContent() {
		return this.content;
	}

	public Map<String,Link<T,?>> getLinks() {
		return this.links;
	}

	public DocumentDescriptor<T> getDocumentDescriptor() {
		return this.documentDescriptor;
	}

	public Date getLoadedAt() {
		return this.loadedAt;
	}

	public boolean isExpired() {
		return System.currentTimeMillis() - this.loadedAt.getTime() > this.documentDescriptor.getMaxAgeInCache();
	}

	private Map<String,Link<T,?>> parseLinks() {
		final Map<String,Link<T,?>> links = new LinkedHashMap<>();
		for(final LinkDescriptor<T,?> linkDescriptor : this.documentDescriptor.getLinkDescriptors()) {
			final Link<T,?> link = linkDescriptor.createLink(this);
			links.put(link.getRole(), link);
		}
		return links;
	}
}
