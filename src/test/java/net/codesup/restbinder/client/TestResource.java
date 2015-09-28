package net.codesup.restbinder.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.w3._1999.xlink.ActuateType;
import org.w3._1999.xlink.ShowType;

import net.codesup.restbinder.Person;
import net.codesup.restbinder.TestCar;

/**
 * @author Mirko Klemm 2015-09-25
 */
@Path("/")
public class TestResource {
	@Context
	private UriInfo uriInfo;

	@GET
	@Produces("application/x-vnd.rb-object+xml;class=net.codesup.restbinder.TestCar")
	@Path("car/{serialNo}")
	public TestCar getTestCar(final @PathParam("serialNo") int serialNo) {
		return TestCar.builder().withModel("Golf GTI").withDriver().withActuate(ActuateType.ON_REQUEST).withArcrole(Person.class.getName()).withRole("driver").withTitle("Herbert").withShow(ShowType.NEW).withHref(uriInfo.getBaseUriBuilder().path("person").path("Herbert").build().toString()).end().build();
	}

	@GET
	@Produces("application/x-vnd.rb-object+xml;class=net.codesup.restbinder.Person")
	@Path("person/{name}")
	public Person getPerson(final @PathParam("name") String name) {
		return Person.builder().withTitle("Herbert").withAge(35).withLabel("Herbert").withName("Herbert").build();
	}
}
