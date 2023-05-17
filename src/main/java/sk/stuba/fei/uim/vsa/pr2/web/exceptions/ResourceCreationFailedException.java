package sk.stuba.fei.uim.vsa.pr2.web.exceptions;

import sk.stuba.fei.uim.vsa.pr2.model.dto.Message;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ResourceCreationFailedException extends WebApplicationException {

    public ResourceCreationFailedException(Class<?> resourceClass) {
        super(Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new Message(Response.Status.INTERNAL_SERVER_ERROR,
                        "Cannot create resource " + resourceClass.getSimpleName(),
                        new Message.Error("ResourceCreationFailedException",
                                Message.Error.serializeStackTrace(new Throwable().getStackTrace()))))
                .type(MediaType.APPLICATION_JSON)
                .build());
    }
}
