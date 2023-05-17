package sk.stuba.fei.uim.vsa.pr2.auth;

import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class UserSecurityContext implements SecurityContext {

    @Getter
    private final LoggedUser principal;

    @Setter
    private boolean secure;

    public UserSecurityContext(LoggedUser principal) {
        this.principal = principal;
    }

    @Override
    public Principal getUserPrincipal() {
        return principal;
    }

    @Override
    public boolean isUserInRole(String s) {
        return true;
    }

    @Override
    public boolean isSecure() {
        return secure;
    }

    @Override
    public String getAuthenticationScheme() {
        return "Basic";
    }
}
