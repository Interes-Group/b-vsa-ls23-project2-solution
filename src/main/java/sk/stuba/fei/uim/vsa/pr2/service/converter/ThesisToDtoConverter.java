package sk.stuba.fei.uim.vsa.pr2.service.converter;

import sk.stuba.fei.uim.vsa.pr2.model.dto.student.StudentAltDto;
import sk.stuba.fei.uim.vsa.pr2.model.dto.teacher.TeacherAltDto;
import sk.stuba.fei.uim.vsa.pr2.model.dto.thesis.ThesisDto;
import sk.stuba.fei.uim.vsa.pr2.model.entity.Student;
import sk.stuba.fei.uim.vsa.pr2.model.entity.Teacher;
import sk.stuba.fei.uim.vsa.pr2.model.entity.Thesis;
import sk.stuba.fei.uim.vsa.pr2.model.entity.ThesisStatus;
import sk.stuba.fei.uim.vsa.pr2.service.ThesisServiceUtils;

public class ThesisToDtoConverter extends EntityToDtoConverter<Thesis> {

    public ThesisToDtoConverter() {
        super();
        addConverter(ThesisDto.class, thesis -> {
            ThesisDto dto = new ThesisDto();
            if (thesis == null) return dto;
            dto.setId(thesis.getId());
            dto.setRegistrationNumber(thesis.getRegistrationNumber());
            dto.setTitle(thesis.getTitle());
            dto.setDescription(thesis.getDescription());
            dto.setDepartment(thesis.getDepartment());
            dto.setSupervisor(EntityToDtoConverterFactory.forEntity(Teacher.class)
                    .convert(thesis.getSupervisor(), TeacherAltDto.class));
            dto.setAuthor(EntityToDtoConverterFactory.forEntity(Student.class)
                    .convert(thesis.getAuthor(), StudentAltDto.class));
            dto.setPublishedOn(ThesisServiceUtils.asLocalDate(thesis.getPublishedOn()));
            dto.setDeadline(ThesisServiceUtils.asLocalDate(thesis.getDeadline()));
            dto.setType(thesis.getType());
            dto.setStatus(convertStatus(thesis.getStatus()));
            return dto;
        });
    }

    private ThesisDto.Status convertStatus(ThesisStatus status) {
        switch (status) {
            case FREE_TO_TAKE:
                return ThesisDto.Status.FREE_TO_TAKE;
            case IN_PROGRESS:
                return ThesisDto.Status.IN_PROGRESS;
            case SUBMITTED:
                return ThesisDto.Status.SUBMITTED;
            default:
                return null;
        }
    }

}
