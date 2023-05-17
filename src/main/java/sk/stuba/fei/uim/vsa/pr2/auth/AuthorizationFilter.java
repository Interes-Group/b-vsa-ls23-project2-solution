package sk.stuba.fei.uim.vsa.pr2.auth;

import lombok.extern.slf4j.Slf4j;
import sk.stuba.fei.uim.vsa.pr2.auth.annotations.Authorize;
import sk.stuba.fei.uim.vsa.pr2.auth.annotations.Secured;
import sk.stuba.fei.uim.vsa.pr2.auth.expresions.AuthorizationExpression;
import sk.stuba.fei.uim.vsa.pr2.model.dto.Message;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@Slf4j
@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext request) throws IOException {
        Authorize[] annotations = resourceInfo.getResourceMethod().getAnnotationsByType(Authorize.class);
        if (annotations == null || annotations.length == 0) return;
        boolean result = Arrays.stream(annotations).anyMatch(annotation ->
                Arrays.stream(annotation.value()).allMatch(clazz -> evaluateAuthorizationExpression(clazz, request))
        );
        if (!result) {
            request.abortWith(Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(Message.errorMessage(Response.Status.FORBIDDEN,
                            "Authenticated user does not have permission for this request",
                            Response.Status.FORBIDDEN.getReasonPhrase(),
                            new Throwable().getStackTrace()))
                    .type(MediaType.APPLICATION_JSON)
                    .build());
        }
    }

    private boolean evaluateAuthorizationExpression(Class<? extends AuthorizationExpression> expressionClass, ContainerRequestContext request) {
        try {
            AuthorizationExpression expression = expressionClass.getConstructor().newInstance();
            return expression.authorize(request);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }
}
