package sk.stuba.fei.uim.vsa.pr2.auth;

import lombok.extern.slf4j.Slf4j;
import sk.stuba.fei.uim.vsa.pr2.BCryptService;
import sk.stuba.fei.uim.vsa.pr2.auth.annotations.Secured;
import sk.stuba.fei.uim.vsa.pr2.model.dto.Message;
import sk.stuba.fei.uim.vsa.pr2.model.entity.Student;
import sk.stuba.fei.uim.vsa.pr2.model.entity.Teacher;
import sk.stuba.fei.uim.vsa.pr2.service.ThesisService;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Base64;

@Slf4j
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext request) throws IOException {
        String authHeader = request.getHeaderString(HttpHeaders.AUTHORIZATION);
        log.info("Authentication for " + authHeader);
        if (authHeader == null || !authHeader.contains("Basic")) {
            request.abortWith(Response
                    .status(Response.Status.UNAUTHORIZED)
                    .header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"VSA\"")
                    .entity(Message.errorMessage(Response.Status.UNAUTHORIZED,
                            "Wrong credentials or wrong format",
                            Response.Status.UNAUTHORIZED.getReasonPhrase(),
                            new Throwable().getStackTrace()))
                    .type(MediaType.APPLICATION_JSON)
                    .build());
            return;
        }
        try {
            String[] credentials = parseHeader(authHeader);
            final SecurityContext securityContext = request.getSecurityContext();
            LoggedUser user = authenticateTeacher(credentials[0], credentials[1]);
            if (user != null) {
                request.setSecurityContext(createSecurityContext(user, securityContext));
            } else {
                user = authenticateStudent(credentials[0], credentials[1]);
                if (user != null) {
                    request.setSecurityContext(createSecurityContext(user, securityContext));
                } else {
                    request.abortWith(Response
                            .status(Response.Status.UNAUTHORIZED)
                            .header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"VSA\"")
                            .entity(Message.errorMessage(Response.Status.UNAUTHORIZED,
                                    "Wrong credentials or wrong format",
                                    Response.Status.UNAUTHORIZED.getReasonPhrase(),
                                    new Throwable().getStackTrace()))
                            .type(MediaType.APPLICATION_JSON)
                            .build());
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            request.abortWith(Response
                    .status(Response.Status.UNAUTHORIZED)
                    .header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"VSA\"")
                    .entity(Message.errorMessage(Response.Status.UNAUTHORIZED,
                            "Wrong credentials or wrong format",
                            ex.getClass().getSimpleName(),
                            ex.getStackTrace()))
                    .type(MediaType.APPLICATION_JSON)
                    .build());
        }
    }

    private String[] parseHeader(String authHeader) {
        String decoded = new String(Base64.getDecoder().decode(authHeader.replace("Basic", "").trim()));
        String[] parts = decoded.split(":");
        if (parts.length != 2)
            throw new IllegalArgumentException("Authorization header is in wrong format");
        return parts;
    }

    private LoggedUser authenticateTeacher(String email, String password) {
        Teacher teacher = ThesisService.getInstance().getTeacher(email);
        return teacher == null || !BCryptService.verify(password, teacher.getPassword()) ? null :
                new LoggedUser(teacher.getAisId(),
                        teacher.getEmail(),
                        teacher.getName(),
                        teacher.getClass());
    }

    private LoggedUser authenticateStudent(String email, String password) {
        Student student = ThesisService.getInstance().getStudent(email);
        return student == null || !BCryptService.verify(password, student.getPassword()) ? null :
                new LoggedUser(student.getAisId(),
                        student.getEmail(),
                        student.getName(),
                        student.getClass());
    }

    private SecurityContext createSecurityContext(LoggedUser user, SecurityContext old) {
        UserSecurityContext context = new UserSecurityContext(user);
        context.setSecure(old.isSecure());
        return context;
    }
}
