package sk.stuba.fei.uim.vsa.pr2.web;

import lombok.extern.slf4j.Slf4j;
import sk.stuba.fei.uim.vsa.pr2.auth.annotations.Authorize;
import sk.stuba.fei.uim.vsa.pr2.auth.annotations.Secured;
import sk.stuba.fei.uim.vsa.pr2.auth.expresions.IdOfLoggedUserMatchesPathParamId;
import sk.stuba.fei.uim.vsa.pr2.auth.expresions.IsTeacher;
import sk.stuba.fei.uim.vsa.pr2.model.dto.student.CreateStudentDto;
import sk.stuba.fei.uim.vsa.pr2.model.dto.student.StudentWithThesisDto;
import sk.stuba.fei.uim.vsa.pr2.model.entity.Student;
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
@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
public class StudentWebResource extends WebResource<Student> {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createStudent(@Valid CreateStudentDto createStudentDto) { // TODO implement validation without additional dependency
        Student student = service.createStudent(createStudentDto.getAisId(),
                createStudentDto.getName(),
                createStudentDto.getEmail(),
                hashPassword(createStudentDto.getPassword()));
        if (student == null)
            throw new ResourceCreationFailedException(Student.class);
        student.setYear(createStudentDto.getYear());
        student.setTerm(createStudentDto.getTerm());
        student.setStudyProgramme(createStudentDto.getProgramme());
        student = service.updateStudent(student);
        return Response
                .status(Response.Status.CREATED)
                .entity(converter.convert(student, StudentWithThesisDto.class))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @GET
    @Secured
    public List<StudentWithThesisDto> getAllStudents() {
        return service.getStudents().stream()
                .map(student -> converter.convert(student, StudentWithThesisDto.class))
                .collect(Collectors.toList());
    }

    @GET
    @Secured
    @Path("/{id}")
    public StudentWithThesisDto getStudent(@PathParam("id") Long studentId) {
        Student student = service.getStudent(studentId);
        if (student == null) {
            throw new EntityNotFoundException(Student.class, studentId);
        }
        return converter.convert(student, StudentWithThesisDto.class);
    }

    @DELETE
    @Secured
    @Authorize({IsTeacher.class})
    @Authorize({IdOfLoggedUserMatchesPathParamId.class})
    @Path("/{id}")
    public StudentWithThesisDto deleteStudent(@PathParam("id") Long studentId) {
        Student student = service.deleteStudent(studentId);
        if (student == null) {
            throw new ResourceDeletionFailedException(Student.class);
        }
        return converter.convert(student, StudentWithThesisDto.class);
    }

}
