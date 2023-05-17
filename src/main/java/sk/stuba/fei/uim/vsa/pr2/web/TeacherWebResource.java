package sk.stuba.fei.uim.vsa.pr2.web;

import lombok.extern.slf4j.Slf4j;
import sk.stuba.fei.uim.vsa.pr2.auth.annotations.Authorize;
import sk.stuba.fei.uim.vsa.pr2.auth.annotations.Secured;
import sk.stuba.fei.uim.vsa.pr2.auth.expresions.IdOfLoggedUserMatchesPathParamId;
import sk.stuba.fei.uim.vsa.pr2.auth.expresions.IsTeacher;
import sk.stuba.fei.uim.vsa.pr2.model.dto.teacher.CreateTeacherDto;
import sk.stuba.fei.uim.vsa.pr2.model.dto.teacher.TeacherWithThesesDto;
import sk.stuba.fei.uim.vsa.pr2.model.entity.Teacher;
import sk.stuba.fei.uim.vsa.pr2.service.converter.EntityToDtoConverter;
import sk.stuba.fei.uim.vsa.pr2.service.converter.EntityToDtoConverterFactory;
import sk.stuba.fei.uim.vsa.pr2.web.exceptions.EntityNotFoundException;
import sk.stuba.fei.uim.vsa.pr2.web.exceptions.ResourceCreationFailedException;
import sk.stuba.fei.uim.vsa.pr2.web.exceptions.ResourceDeletionFailedException;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Path("/teachers")
@Produces(MediaType.APPLICATION_JSON)
public class TeacherWebResource extends WebResource<Teacher> {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTeacher(@Valid CreateTeacherDto createTeacherDto) {
        Teacher teacher = service.createTeacher(
                createTeacherDto.getAisId(),
                createTeacherDto.getName(),
                createTeacherDto.getEmail(),
                hashPassword(createTeacherDto.getPassword()),
                createTeacherDto.getDepartment()
        );
        if (teacher == null) {
            throw new ResourceCreationFailedException(Teacher.class);
        }
        teacher.setInstitute(createTeacherDto.getInstitute());
        teacher = service.updateTeacher(teacher);
        return Response
                .status(Response.Status.CREATED)
                .type(MediaType.APPLICATION_JSON)
                .entity(converter.convert(teacher, TeacherWithThesesDto.class))
                .build();
    }

    @GET
    @Secured
    public List<TeacherWithThesesDto> getAllTeachers() {
        return service.getTeachers().stream()
                .map(t -> converter.convert(t, TeacherWithThesesDto.class))
                .collect(Collectors.toList());
    }

    @GET
    @Secured
    @Path("/{id}")
    public TeacherWithThesesDto getTeacher(@PathParam("id") Long teacherId) {
        Teacher teacher = service.getTeacher(teacherId);
        if (teacher == null) {
            throw new EntityNotFoundException(Teacher.class, teacherId);
        }
        return converter.convert(teacher, TeacherWithThesesDto.class);
    }

    @DELETE
    @Secured
    @Authorize({IsTeacher.class, IdOfLoggedUserMatchesPathParamId.class})
    @Path("/{id}")
    public TeacherWithThesesDto deleteTeacher(@PathParam("id") Long teacherId) {
        Teacher teacher = service.deleteTeacher(teacherId);
        if (teacher == null) {
            throw new ResourceDeletionFailedException(Teacher.class);
        }
        return converter.convert(teacher, TeacherWithThesesDto.class);
    }

}
