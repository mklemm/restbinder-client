package net.codesup.restbinder.client;

import net.codesup.restbinder.client.http.HttpResponse;

/**
 * @author Mirko Klemm 2015-09-24
 */
public interface MessageBodyHandler {
	<T> WebDocument<T> handle(final ClientSession clientSession, final HttpResponse response);
}
