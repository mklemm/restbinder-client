package net.codesup.restbinder.client;

import java.net.URI;

/**
 * @author Mirko Klemm 2015-09-23
 */
public class Link<P> {
	private final LinkDescriptor<P> descriptor;
	private final WebDocument<P> parent;
	private final URI href;
	private final String title;
	private final String role;
	private final String arcRole;

	Link(final LinkDescriptor<P> descriptor, final WebDocument<P> parent, final URI href, final String title, final String role, final String arcRole) {
		this.descriptor = descriptor;
		this.parent = parent;
		this.href = href;
		this.title = title;
		this.role = role;
		this.arcRole = arcRole;
	}

	public LinkDescriptor<P> getDescriptor() {
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

	public String getArcRole() {
		return this.arcRole;
	}

	public <S> WebDocument<S> resolve(final Class<S> supplierType) {
		return this.parent.getDocumentDescriptor().getClientSession().get(supplierType, this.href);
	}
}
