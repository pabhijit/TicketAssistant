package com.walmart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.LinkedHashMap;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTestNg;
import org.glassfish.jersey.test.TestProperties;
import org.testng.annotations.Test;

import com.walmart.resource.TicketResource;

@SuppressWarnings("unchecked")
public class TicketServiceTest  extends JerseyTestNg.ContainerPerClassTest{

	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(TicketResource.class);
	}

	@Test
	public void testNumSeatsAvailable(){

		try{
			// Level id not provided [Should return information for all levels]
			final List<LinkedHashMap<String, String>> multiLevel = target("seat").path("level/").register(new JacksonFeature()).request().get(List.class);
			assertEquals(4, multiLevel.size()); 
			assertEquals("Orchestra",multiLevel.get(0).get("levelName"));
			assertEquals("Main",multiLevel.get(1).get("levelName"));
			assertEquals("Balcony1",multiLevel.get(2).get("levelName"));
			assertEquals("Balcony2",multiLevel.get(3).get("levelName"));

			// Level id provided [Should return information for level 2]
			final List<LinkedHashMap<String, String>> singleLevel = target("seat").path("level/2").register(new JacksonFeature()).request().get(List.class);
			assertEquals(1, singleLevel.size()); 
			assertEquals(75,singleLevel.get(0).get("price"));

			// Level id provided is invalid [Should return 404 and custom message]
			//Scenario verified as working from Postman(Rest Client). {"code": 8404,"message": "215 is not a valid level id.","status": 404}
			//TODO: Exception mapper is not working with testng(Need to check)
			/*
			final List<LinkedHashMap<String, String>> invalidLevel = target("seat").path("level/215").register(new JacksonFeature()).request().get(List.class);
			 */

		} catch(Exception ex) {
			ex.printStackTrace();
			fail("Exception thrown from testNumSeatsAvailable.");
		}
	}

	@Test
	public void testFindAndHoldSeats(){

		try{
			// Ticket held [Available tickets must be lessened]
			final Response response = target("seat").queryParam("numSeats", 18).queryParam("minLevel", 2)
					.queryParam("maxLevel", 2).queryParam("email", "abc@gmail.com")
					.register(new JacksonFeature()).request().buildPost(Entity.text("")).invoke();

			assertEquals(302, response.getStatus());    //Custom status from resource.
			final List<LinkedHashMap<String, String>> check1 = target("seat").path("level/2").register(new JacksonFeature()).request().get(List.class);
			assertEquals(1982, check1.get(0).get("availableSeats"));

			//Ticket held but not booked [The tickets must be added back to the available tickets]
			Thread.sleep(31000);

			final List<LinkedHashMap<String, String>> check2 = target("seat").path("level/2").register(new JacksonFeature()).request().get(List.class);
			assertEquals(2000, check2.get(0).get("availableSeats"));

		} catch(Exception ex) {
			ex.printStackTrace();
			fail("Exception thrown from testNumSeatsAvailable.");
		}
	}

	@Test
	public void testReserveSeats(){

		try{
			//Hold seats and then book.
			final Response response = target("seat").queryParam("numSeats", 18).queryParam("minLevel", 2)
					.queryParam("maxLevel", 2).queryParam("email", "abc@gmail.com")
					.register(new JacksonFeature()).request().buildPost(Entity.text("")).invoke();

			final Response check3 = target("seat").path("102/abc@gmail.com").register(new JacksonFeature()).request().put(Entity.text(""));
			assertEquals(201, check3.getStatus());
		} catch(Exception ex) {
			ex.printStackTrace();
			fail("Exception thrown from testNumSeatsAvailable.");
		}
	}

}
