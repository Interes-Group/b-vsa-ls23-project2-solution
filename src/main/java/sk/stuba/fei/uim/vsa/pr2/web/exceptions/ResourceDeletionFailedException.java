package sk.stuba.fei.uim.vsa.pr2.web.exceptions;

import sk.stuba.fei.uim.vsa.pr2.model.dto.Message;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ResourceDeletionFailedException extends WebApplicationException {

    public ResourceDeletionFailedException(Class<?> resourceClass) {
        super(Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Message.errorMessage(Response.Status.INTERNAL_SERVER_ERROR,
                        "Cannot delete resource " + resourceClass.getSimpleName(),
                        "ResourceDeletionFailedException",
                        new Throwable().getStackTrace()))
                .type(MediaType.APPLICATION_JSON)
                .build());
    }
}
