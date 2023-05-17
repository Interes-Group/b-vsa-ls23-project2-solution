package sk.stuba.fei.uim.vsa.pr2.auth;

import lombok.Data;

import java.security.Principal;

@Data
public class LoggedUser implements Principal {

    private Long id;
    private String email;
    private String givenName;
    private Type type;

    public LoggedUser() {
    }

    public LoggedUser(Long id, String email, String givenName) {
        this.id = id;
        ;
        this.email = email;
        this.givenName = givenName;
    }

    public LoggedUser(Long id, String email, String givenName, Type type) {
        this.id = id;
        this.email = email;
        this.givenName = givenName;
        this.type = type;
    }

    public LoggedUser(Long id, String email, String givenName, Class<?> type) {
        this.id = id;
        this.email = email;
        this.givenName = givenName;
        this.type = Type.valueOf(type.getSimpleName().toUpperCase());
    }


    @Override
    public String getName() { // Toto by malo vracať username ktorým sa user prihlasuje
        return email;
    }

    public static enum Type {
        STUDENT,
        TEACHER
    }
}
