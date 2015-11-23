package com.walmart.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.walmart.model.ErrorMessage;

@Provider
public class InvalidRequestExceptionMapper implements ExceptionMapper<InvalidRequestException>{

	@Override
	public Response toResponse(InvalidRequestException dnfe) {
		ErrorMessage errorMessage = new ErrorMessage(8400,dnfe.getMessage(),Status.BAD_REQUEST.getStatusCode());
		return Response.status(Status.BAD_REQUEST).entity(errorMessage).build();
	}

}
