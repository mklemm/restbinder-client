package net.codesup.restbinder.client;

import net.codesup.restbinder.client.http.HttpResponse;

/**
 * @author Mirko Klemm 2015-09-24
 */
public interface DocumentMarshaller {
	<T> WebDocument<T> unmarshal(final DocumentDescriptor<T> documentDescriptor, final HttpResponse response);
}
