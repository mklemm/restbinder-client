package net.codesup.restbinder.client;

import java.util.List;

import javax.xml.bind.JAXBElement;

/**
 * Created by klemm0 on 2015-09-22.
 */
public class JaxbLinkParser<T> implements LinkParser<T> {
	@Override public List<Link<T, ?>> createLinksFromContent(final T content) {
		return null;
	}

	@Override public Class<T> getContentType() {
		return JAXBElement.class;
	}
}
