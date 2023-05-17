package sk.stuba.fei.uim.vsa.pr2.web;

import lombok.extern.slf4j.Slf4j;
import sk.stuba.fei.uim.vsa.pr2.auth.LoggedUser;
import sk.stuba.fei.uim.vsa.pr2.auth.annotations.Authorize;
import sk.stuba.fei.uim.vsa.pr2.auth.annotations.Secured;
import sk.stuba.fei.uim.vsa.pr2.auth.expresions.IsTeacher;
import sk.stuba.fei.uim.vsa.pr2.model.dto.Message;
import sk.stuba.fei.uim.vsa.pr2.model.dto.StudentIdDto;
import sk.stuba.fei.uim.vsa.pr2.model.dto.thesis.CreateThesisDto;
import sk.stuba.fei.uim.vsa.pr2.model.dto.thesis.ThesisDto;
import sk.stuba.fei.uim.vsa.pr2.model.entity.Student;
import sk.stuba.fei.uim.vsa.pr2.model.entity.Thesis;
import sk.stuba.fei.uim.vsa.pr2.web.exceptions.EntityNotFoundException;
import sk.stuba.fei.uim.vsa.pr2.web.exceptions.ResourceCreationFailedException;
import sk.stuba.fei.uim.vsa.pr2.web.exceptions.ResourceDeletionFailedException;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Secured
@Path("/theses")
@Produces(MediaType.APPLICATION_JSON)
public class ThesisWebResource extends WebResource<Thesis> {

    @POST
    @Authorize({IsTeacher.class})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createThesis(@Valid CreateThesisDto createThesisDto, @Context SecurityContext securityContext) {
        LoggedUser teacher = (LoggedUser) securityContext.getUserPrincipal();
        Thesis thesis = service.makeThesisAssignment(
                teacher.getId(),
                createThesisDto.getRegistrationNumber(),
                createThesisDto.getTitle(),
                createThesisDto.getType().toString(),
                createThesisDto.getDescription());
        if (thesis == null) {
            throw new ResourceCreationFailedException(Thesis.class);
        }
        return Response
                .status(Response.Status.CREATED)
                .type(MediaType.APPLICATION_JSON)
                .entity(converter.convert(thesis, ThesisDto.class))
                .build();
    }

    @GET
    public List<ThesisDto> getAllTheses() {
        return service.getTheses().stream()
                .map(t -> converter.convert(t, ThesisDto.class))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public ThesisDto getThesis(@PathParam("id") Long thesisId) {
        Thesis thesis = service.getThesis(thesisId);
        if (thesis == null) {
            throw new EntityNotFoundException(Thesis.class, thesisId);
        }
        return converter.convert(thesis, ThesisDto.class);
    }

    @DELETE
    @Authorize({IsTeacher.class})
    @Path("/{id}")
    public ThesisDto deleteThesis(@PathParam("id") Long thesisId, @Context SecurityContext securityContext) {
        LoggedUser teacher = (LoggedUser) securityContext.getUserPrincipal();
        Thesis thesis = service.getThesis(thesisId);
        if (thesis == null) {
            throw new EntityNotFoundException(Thesis.class, thesisId);
        }
        if (!Objects.equals(thesis.getSupervisor().getAisId(), teacher.getId())) {
            throw new ForbiddenException(Response
                    .status(Response.Status.FORBIDDEN)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(Message.errorMessage(
                            Response.Status.FORBIDDEN,
                            "Logged teacher is not supervisor for the thesis",
                            "ForbiddenException",
                            new Throwable().getStackTrace()))
                    .build());
        }
        thesis = service.deleteThesis(thesisId);
        if (thesis == null) {
            throw new ResourceDeletionFailedException(Thesis.class);
        }
        return converter.convert(thesis, ThesisDto.class);
    }

    @POST
    @Path("/{id}/assign")
    @Consumes(MediaType.APPLICATION_JSON)
    public ThesisDto assignThesis(@PathParam("id") Long thesisId, StudentIdDto studentIdDto, @Context SecurityContext securityContext) {
        LoggedUser user = (LoggedUser) securityContext.getUserPrincipal();
        Long studentId = user.getType() == LoggedUser.Type.STUDENT ? user.getId() : studentIdDto.getStudentId();
        if (user.getType() == LoggedUser.Type.TEACHER) {
            Student student = service.getStudent(studentId);
            if (student == null) {
                throw new IllegalArgumentException("Cannot finds student with id '" + studentId + "'");
            }
            studentId = student.getAisId();
        }
        Thesis thesis = service.assignThesis(thesisId, studentId);
        if (thesis == null) {
            throw new IllegalStateException("Cannot assign student '" + studentId + "' to thesis with id '" + thesisId + "'");
        }
        return converter.convert(thesis, ThesisDto.class);
    }

    @POST
    @Path("/{id}/submit")
    @Consumes(MediaType.APPLICATION_JSON)
    public ThesisDto submitThesis(@PathParam("id") Long thesisId, StudentIdDto studentIdDto, @Context SecurityContext securityContext) {
        LoggedUser user = (LoggedUser) securityContext.getUserPrincipal();
        Long studentId = user.getType() == LoggedUser.Type.STUDENT ? user.getId() : studentIdDto.getStudentId();
        if (user.getType() == LoggedUser.Type.TEACHER) {
            Student student = service.getStudent(studentId);
            if (student == null) {
                throw new IllegalArgumentException("Cannot finds student with id '" + studentIdDto.getStudentId() + "'");
            }
            studentId = student.getAisId();
        }
        Thesis thesis = service.submitThesis(thesisId, studentId);
        if (thesis == null) {
            throw new IllegalStateException("Cannot submit thesis with id '" + thesisId + "'");
        }
        return converter.convert(thesis, ThesisDto.class);
    }

}
