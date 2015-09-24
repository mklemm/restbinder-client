package net.codesup.restbinder.client;

import net.codesup.restbinder.client.http.MediaType;

/**
 * @author Mirko Klemm 2015-09-24
 */
public interface DocumentDescriptorFactory {
	<T> DocumentDescriptor<T> createDocumentDescriptor(final ClientSession clientSession, final MediaType contentType);
}
