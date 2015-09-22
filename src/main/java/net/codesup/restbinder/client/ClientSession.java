package net.codesup.restbinder.client;

import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by klemm0 on 2015-09-22.
 */
public class ClientSession {
	private final Map<URI,WebDocument> webDocumentCache = new WeakHashMap<>();

	public ClientSession() {
		this.rootUri = rootUri;
	}

	/**
	 * Internal use only
	 * @param type
	 * @param uri
	 * @param <T>
	 * @return
	 */
	<T> Collection<WebDocument<T>> get(final Class<T> type, final URI uri) {

		return null;
	}


}
