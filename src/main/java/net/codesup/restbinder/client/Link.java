package net.codesup.restbinder.client;

import java.net.URI;

/**
 * @author Mirko Klemm 2015-09-23
 */
public class Link<P,S> {
	private final LinkDescriptor<P,S> descriptor;
	private final WebDocument<P> parent;
	private final URI href;
	private final String title;
	private final String role;

	Link(final LinkDescriptor<P, S> descriptor, final WebDocument<P> parent, final URI href, final String title, final String role) {
		this.descriptor = descriptor;
		this.parent = parent;
		this.href = href;
		this.title = title;
		this.role = role;
	}

	public LinkDescriptor<P, S> getDescriptor() {
		return this.descriptor;
	}

	public WebDocument<P> getParent() {
		return this.parent;
	}

	public URI getHref() {
		return this.href;
	}

	public String getTitle() {
		return this.title;
	}

	public String getRole() {
		return this.role;
	}

	public WebDocument<S> resolve() {
		return this.parent.getDocumentDescriptor().getClientSession().get(this.href);
	}
}
