package com.walmart.resource;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import com.walmart.model.Level;
import com.walmart.model.SeatHold;
import com.walmart.resource.bean.SeatsFilter;
import com.walmart.service.TicketService;
import com.walmart.service.impl.TicketServiceImpl;

/**
 * Root resource (exposed at "seat" path)
 */
@Path("seat")
public class TicketResource {

	//@Autowired
	private TicketService ticketService = new TicketServiceImpl();

	/**
	 * This method will return the number of available tickets
	 * optionally by level.
	 * @param Level id (optional)
	 * @return List of levels with available tickets.
	 */
	
	@GET
	@Path("/level/{levelID : (\\w+)?}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Level> numSeatsAvailable(@PathParam("levelID") Integer venueLevel) {

		Optional<Integer> level = venueLevel == null? Optional.empty():Optional.of(venueLevel);
		List<Level> levelInfo = ticketService.numSeatsAvailable(level);
		return levelInfo;

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAndHoldSeats(@BeanParam SeatsFilter filter, @Context UriInfo uriInfo) {

		Optional<Integer> minLevel = filter.getMinLevel() == null?Optional.empty(): Optional.of(filter.getMinLevel());
		Optional<Integer> maxLevel = filter.getMaxLevel() == null?Optional.empty(): Optional.of(filter.getMaxLevel());
		SeatHold seatHold = ticketService.findAndHoldSeats(filter.getNumSeats(), 
				minLevel, maxLevel, filter.getCustomerEmail());
		seatHold.addLink(getUriForBook(uriInfo, seatHold), "Book Uri");
		return Response.status(Status.FOUND).entity(seatHold).build();
	}

	@PUT
	@Path("/{seatHoldId}/{customerEmail}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response reserveSeats(@PathParam("seatHoldId") int seatHoldId, @PathParam("customerEmail") String customerEmail) {

		String response = ticketService.reserveSeats(seatHoldId, customerEmail);

		return Response.status(Status.CREATED).entity(response).build();
	}

	private String getUriForBook(UriInfo uriInfo, SeatHold seatHold) {
		String uri = uriInfo.getBaseUriBuilder().path("seat")
				.path(Integer.toString(seatHold.getSeatHoldID()))
				.path(seatHold.getCustomer().getEmail())
				.build()
				.toString();
		return uri;
	}

}
