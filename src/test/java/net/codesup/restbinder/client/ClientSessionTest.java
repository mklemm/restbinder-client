package net.codesup.restbinder.client;

import java.net.URI;

import org.junit.Assert;
import org.junit.Test;

import net.codesup.restbinder.client.jaxb.JaxbDocumentDescriptorFactory;
import net.codesup.restbinder.client.jaxb.JaxbMessageBodyHandlerFactory;

/**
 * @author Mirko Klemm 2015-09-24
 */
public class ClientSessionTest {
	@Test
	public void testGet() {
		final ClientSession clientSession = new ClientSession(new JaxbMessageBodyHandlerFactory("rb"), new JaxbDocumentDescriptorFactory(), 1000);
		WebDocument<?> d = clientSession.get(URI.create("http://charon.local/glassfish/test"));
		Assert.assertNotNull(d);
	}
}
