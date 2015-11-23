package com.walmart.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.walmart.model.ErrorMessage;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException>{

	@Override
	public Response toResponse(DataNotFoundException dnfe) {
		ErrorMessage errorMessage = new ErrorMessage(8404,dnfe.getMessage(),Status.NOT_FOUND.getStatusCode());
		return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
	}

}
