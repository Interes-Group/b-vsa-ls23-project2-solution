package sk.stuba.fei.uim.vsa.pr2.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.extern.slf4j.Slf4j;
import sk.stuba.fei.uim.vsa.pr2.auth.annotations.Secured;
import sk.stuba.fei.uim.vsa.pr2.model.Page;
import sk.stuba.fei.uim.vsa.pr2.model.PageImpl;
import sk.stuba.fei.uim.vsa.pr2.model.PageableImpl;
import sk.stuba.fei.uim.vsa.pr2.model.dto.Message;
import sk.stuba.fei.uim.vsa.pr2.model.dto.student.StudentSearchDto;
import sk.stuba.fei.uim.vsa.pr2.model.dto.student.StudentWithThesisDto;
import sk.stuba.fei.uim.vsa.pr2.model.dto.teacher.TeacherSearchDto;
import sk.stuba.fei.uim.vsa.pr2.model.dto.teacher.TeacherWithThesesDto;
import sk.stuba.fei.uim.vsa.pr2.model.dto.thesis.ThesisDto;
import sk.stuba.fei.uim.vsa.pr2.model.dto.thesis.ThesisSearchDto;
import sk.stuba.fei.uim.vsa.pr2.model.entity.Student;
import sk.stuba.fei.uim.vsa.pr2.model.entity.Teacher;
import sk.stuba.fei.uim.vsa.pr2.model.entity.Thesis;
import sk.stuba.fei.uim.vsa.pr2.service.ThesisService;
import sk.stuba.fei.uim.vsa.pr2.service.converter.EntityToDtoConverter;
import sk.stuba.fei.uim.vsa.pr2.service.converter.EntityToDtoConverterFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;

import static sk.stuba.fei.uim.vsa.pr2.service.ThesisServiceUtils.asDate;

@Slf4j
@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
public class BonusSearchWebService {

    private final EntityToDtoConverter<Thesis> thesisConverter = EntityToDtoConverterFactory.forEntity(Thesis.class);
    private final EntityToDtoConverter<Teacher> teacherConverter = EntityToDtoConverterFactory.forEntity(Teacher.class);
    private final EntityToDtoConverter<Student> studentConverter = EntityToDtoConverterFactory.forEntity(Student.class);
    private final ThesisService service = ThesisService.getInstance();
    private final ObjectMapper mapper;

    public BonusSearchWebService() {
        mapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        mapper.registerModule(javaTimeModule);
//        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @POST
    @Secured
    @Path("/theses")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response searchTheses(String dtoJson, @QueryParam("page") Integer page, @QueryParam("size") Integer size) throws JsonProcessingException {
        ThesisSearchDto dto = null;
        try {
            ObjectMapper om = new ObjectMapper();
            dto = om.readValue(dtoJson, ThesisSearchDto.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new IllegalArgumentException("Cannot parse request body", e);
        }
        PageableImpl pageable = new PageableImpl(page, size);
        if (dto.getStudentId() != null && dto.getTeacherId() != null) {
            throw new BadRequestException(Response
                    .status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(Message.errorMessage(
                            Response.Status.BAD_REQUEST,
                            "Search attribute studentId and teacherId cannot be present at the same time. Pick one of those",
                            "BadRequestException",
                            new Throwable().getStackTrace()))
                    .build());
        }
        Page<Thesis> theses = service.findTheses(
                Optional.ofNullable(dto.getStudentId()),
                Optional.ofNullable(dto.getTeacherId()),
                Optional.ofNullable(dto.getDepartment()),
                Optional.ofNullable(asDate(dto.getPublishedOnAsLocalDate())),
                Optional.ofNullable(dto.getType() == null ? null : dto.getType()),
                Optional.ofNullable(dto.getStatus() == null ? null : dto.getStatus()),
                pageable);
        Page<ThesisDto> finalPage = new PageImpl<>(theses.getContent().stream()
                .map(t -> thesisConverter.convert(t, ThesisDto.class))
                .collect(Collectors.toList()), theses.getPage());
        String json = mapper.writeValueAsString(finalPage);
        log.info("Search of theses page: " + json);
        return Response.ok(json).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    @POST
    @Secured
    @Path("/teachers")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response searchTeacher(TeacherSearchDto dto, @QueryParam("page") Integer page, @QueryParam("size") Integer size) throws JsonProcessingException {
        PageableImpl pageable = new PageableImpl(page, size);
        Page<Teacher> teacherPage = service.findTeachers(
                Optional.ofNullable(dto.getName()),
                Optional.ofNullable(dto.getInstitute()),
                pageable);
        Page<TeacherWithThesesDto> finalPage = new PageImpl<>(teacherPage.getContent().stream()
                .map(t -> teacherConverter.convert(t, TeacherWithThesesDto.class))
                .collect(Collectors.toList()), teacherPage.getPage());
        String json = mapper.writeValueAsString(finalPage);
        log.info("Search of teachers page: " + json);
        return Response.ok(json).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    @POST
    @Secured
    @Path("/students")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response searchStudent(StudentSearchDto dto, @QueryParam("page") Integer page, @QueryParam("size") Integer size) throws JsonProcessingException {
        PageableImpl pageable = new PageableImpl(page, size);
        Page<Student> studentsPage = service.findStudents(
                Optional.ofNullable(dto.getName()),
                Optional.ofNullable(dto.getYear() == null ? null : dto.getYear().toString()),
                pageable);
        Page<StudentWithThesisDto> finalPage = new PageImpl<>(studentsPage.getContent().stream()
                .map(t -> studentConverter.convert(t, StudentWithThesisDto.class))
                .collect(Collectors.toList()), studentsPage.getPage());
        String json = mapper.writeValueAsString(finalPage);
        log.info("Search of students page: " + json);
        return Response.ok(json).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

}
