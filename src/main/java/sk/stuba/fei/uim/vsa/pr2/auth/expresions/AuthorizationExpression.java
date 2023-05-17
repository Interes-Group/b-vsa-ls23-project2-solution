package sk.stuba.fei.uim.vsa.pr2.auth.expresions;

import javax.ws.rs.container.ContainerRequestContext;

public abstract class AuthorizationExpression {

    public abstract boolean authorize(ContainerRequestContext request);

}
