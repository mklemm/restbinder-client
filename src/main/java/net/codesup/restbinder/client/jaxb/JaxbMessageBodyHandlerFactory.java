package net.codesup.restbinder.client.jaxb;

import net.codesup.restbinder.client.MessageBodyHandler;
import net.codesup.restbinder.client.MessageBodyHandlerFactory;
import net.codesup.restbinder.client.http.HttpResponse;
import net.codesup.restbinder.client.http.JaxbMediaType;

/**
 * @author Mirko Klemm 2015-09-24
 */
public class JaxbMessageBodyHandlerFactory implements MessageBodyHandlerFactory {
	private final JaxbMediaType<Void> objectMediaType;

	public JaxbMessageBodyHandlerFactory(final String vendor) {
		this.objectMediaType = new JaxbMediaType<>(vendor, null);
	}

	@Override
	public MessageBodyHandler createHandler(final HttpResponse response) {
		if(response.getContentType().matchesType(this.objectMediaType)) {
			return new JaxbMessageBodyHandler(this.objectMediaType);
		}
		return null;
	}
}
