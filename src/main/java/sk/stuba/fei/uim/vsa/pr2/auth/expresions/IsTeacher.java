package sk.stuba.fei.uim.vsa.pr2.auth.expresions;

import sk.stuba.fei.uim.vsa.pr2.auth.LoggedUser;

import javax.ws.rs.container.ContainerRequestContext;

public class IsTeacher extends AuthorizationExpression {

    @Override
    public boolean authorize(ContainerRequestContext request) {
        return request.getSecurityContext() != null && ((LoggedUser) request.getSecurityContext().getUserPrincipal()).getType() == LoggedUser.Type.TEACHER;
    }
}
