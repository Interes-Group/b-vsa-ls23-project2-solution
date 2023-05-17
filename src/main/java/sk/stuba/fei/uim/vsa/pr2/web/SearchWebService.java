package sk.stuba.fei.uim.vsa.pr2.web;

import lombok.extern.slf4j.Slf4j;
import sk.stuba.fei.uim.vsa.pr2.auth.annotations.Secured;
import sk.stuba.fei.uim.vsa.pr2.model.dto.Message;
import sk.stuba.fei.uim.vsa.pr2.model.dto.UserIdDto;
import sk.stuba.fei.uim.vsa.pr2.model.dto.thesis.ThesisDto;
import sk.stuba.fei.uim.vsa.pr2.model.entity.Thesis;
import sk.stuba.fei.uim.vsa.pr2.service.ThesisService;
import sk.stuba.fei.uim.vsa.pr2.service.converter.EntityToDtoConverter;
import sk.stuba.fei.uim.vsa.pr2.service.converter.EntityToDtoConverterFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Secured
@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
public class SearchWebService {

    private final EntityToDtoConverter<Thesis> thesisConverter = EntityToDtoConverterFactory.forEntity(Thesis.class);
    private final ThesisService service = ThesisService.getInstance();

    @POST
    @Path("/theses")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<ThesisDto> searchTheses(UserIdDto dto) {
        List<Thesis> theses = new ArrayList<>();
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
        if (dto.getStudentId() == null && dto.getTeacherId() == null) {
            theses = service.getTheses();
        } else if (dto.getStudentId() != null) {
            Thesis th = service.getThesisByStudent(dto.getStudentId());
            theses = th != null ? Arrays.asList(th) : new ArrayList<>();
        } else if (dto.getTeacherId() != null) {
            theses = service.getThesesByTeacher(dto.getTeacherId());
        }
        return theses.stream().map(t -> thesisConverter.convert(t, ThesisDto.class)).collect(Collectors.toList());
    }
}
