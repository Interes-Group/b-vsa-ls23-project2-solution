package sk.stuba.fei.uim.vsa.pr2.web.exceptions;

import sk.stuba.fei.uim.vsa.pr2.model.dto.Message;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class EntityNotFoundException extends NotFoundException {

    public EntityNotFoundException(Class<?> entityClass, Long id) {
        super(
                Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(Message.errorMessage(Response.Status.NOT_FOUND,
                                "No " + entityClass.getSimpleName() + " entity was found for id '" + id + "'",
                                "NotFoundException",
                                new Throwable().getStackTrace()))
                        .type(MediaType.APPLICATION_JSON)
                        .build()
        );
    }
}
