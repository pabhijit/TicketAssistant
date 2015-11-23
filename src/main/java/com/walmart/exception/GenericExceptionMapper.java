package com.walmart.exception;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.walmart.model.ErrorMessage;

// It has been disabled in order to try out other ways of throwing exceptions in JAX-RS

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable ex) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setCode(8500);
		errorMessage.setMessage(ex.getMessage());
		setHttpStatus(ex, errorMessage);
		return Response.status(errorMessage.getStatus())
				.entity(errorMessage)
				.build();
	}

	private void setHttpStatus(Throwable ex, ErrorMessage errorMessage) {
		if(ex instanceof ClientErrorException ) {
			errorMessage.setStatus(((ClientErrorException)ex).getResponse().getStatus());
		} else if(ex instanceof ServerErrorException ) {
			errorMessage.setStatus(((ServerErrorException)ex).getResponse().getStatus());
		} else if(ex instanceof WebApplicationException ) {
			errorMessage.setStatus(((WebApplicationException)ex).getResponse().getStatus());
		} else {
			errorMessage.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()); 
		}
	}

}