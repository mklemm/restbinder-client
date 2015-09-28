package net.codesup.restbinder.client;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.net.httpserver.HttpServer;

import net.codesup.restbinder.Person;
import net.codesup.restbinder.TestCar;
import net.codesup.restbinder.client.jaxb.JaxbDocumentDescriptorFactory;

/**
 * @author Mirko Klemm 2015-09-24
 */
public class ClientSessionTest {
	private static HttpServer httpServer = null;

	@BeforeClass
	public static void startServer() throws IOException {
		final ResourceConfig resourceConfig = new PackagesResourceConfig("net.codesup.restbinder.client");
		ClientSessionTest.httpServer = HttpServerFactory.create(UriBuilder.fromUri("http://localhost/test").port(8085).build(), resourceConfig);
		ClientSessionTest.httpServer.start();
	}

	@AfterClass
	public static void stopServer() {
		if(ClientSessionTest.httpServer != null) {
			ClientSessionTest.httpServer.stop(0);
		}
	}

	@Test
	public void testGet() {
		final ClientSession clientSession = new ClientSession(new JaxbDocumentDescriptorFactory("rb"), 1000);
		final WebDocument<?> d = clientSession.get(TestCar.class, URI.create("http://localhost:8085/test/car/2"));
		Assert.assertNotNull(d);

		final WebDocument<Person> p = d.resolve(Person.class, "driver");
		Assert.assertNotNull(p);

		final WebDocument<?> prs = d.resolve("driver");
		Assert.assertNotNull(prs);

		System.out.println(p.getContent().getName());
		System.out.println(((Person)prs.getContent()).getName());
	}
}
