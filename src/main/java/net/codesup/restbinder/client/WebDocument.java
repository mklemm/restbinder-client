package net.codesup.restbinder.client;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by klemm0 on 2015-09-22.
 */
public class WebDocument<T> {
	private final URI uri;
	private final T content;
	private final Map<String,Link<T,?>> links;

	public WebDocument(final URI uri, final T content, final List<Link<T, ?>> links) {
		this.uri = uri;
		this.content = content;
		final Map<String,Link<T,?>> linksBuilding = new HashMap<>();
		for(final Link<T,?> link : links) {
			linksBuilding.put(link.getRole(), link);
		}
		this.links = Collections.unmodifiableMap(linksBuilding);
	}

	public WebDocument(final URI uri, final T content, final Map<String, Link<T, ?>> links) {
		this.uri = uri;
		this.content = content;
		this.links = Collections.unmodifiableMap(new HashMap<>(links));
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


	public Link<T,?> getLink(final String role) {
		return this.links.get(role);
	}
}
