package sk.stuba.fei.uim.vsa.pr2.auth.expresions;

import sk.stuba.fei.uim.vsa.pr2.auth.LoggedUser;

import javax.ws.rs.container.ContainerRequestContext;

public class IdOfLoggedUserMatchesPathParamId extends AuthorizationExpression {

    @Override
    public boolean authorize(ContainerRequestContext request) {
        if (request.getSecurityContext() == null) return false;
        if (request.getUriInfo().getPathParameters().get("id") == null) return false;
        LoggedUser user = (LoggedUser) request.getSecurityContext().getUserPrincipal();
        String id = request.getUriInfo().getPathParameters().get("id").stream().findFirst().orElse("0");
        return user.getId() == Long.parseLong(id);
    }
}
