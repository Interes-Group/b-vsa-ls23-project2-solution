package sk.stuba.fei.uim.vsa.pr2.web.exceptionmappers;

import sk.stuba.fei.uim.vsa.pr2.model.dto.Message;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException e) {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Message.errorMessage(Response.Status.INTERNAL_SERVER_ERROR, e))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
