package sk.stuba.fei.uim.vsa.pr2.service.converter;

import sk.stuba.fei.uim.vsa.pr2.model.dto.teacher.TeacherAltDto;
import sk.stuba.fei.uim.vsa.pr2.model.dto.teacher.TeacherWithThesesDto;
import sk.stuba.fei.uim.vsa.pr2.model.dto.thesis.ThesisDto;
import sk.stuba.fei.uim.vsa.pr2.model.entity.Teacher;
import sk.stuba.fei.uim.vsa.pr2.model.entity.Thesis;

import java.util.Objects;
import java.util.stream.Collectors;

public class TeacherToDtoConverter extends EntityToDtoConverter<Teacher> {

    public TeacherToDtoConverter() {
        super();
        addConverter(TeacherWithThesesDto.class, teacher -> {
            TeacherWithThesesDto dto = new TeacherWithThesesDto();
            if (teacher == null) return dto;
            dto.setId(teacher.getAisId());
            dto.setAisId(teacher.getAisId());
            dto.setName(teacher.getName());
            dto.setEmail(teacher.getEmail());
            dto.setInstitute(teacher.getInstitute());
            dto.setDepartment(teacher.getDepartment());
            dto.setTheses(teacher.getSupervisedTheses() != null ? teacher.getSupervisedTheses().stream()
                    .map(thesis -> EntityToDtoConverterFactory.forEntity(Thesis.class).convert(thesis, ThesisDto.class))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()) : null);
            return dto;
        });
        addConverter(TeacherAltDto.class, teacher -> {
            TeacherAltDto dto = new TeacherAltDto();
            if (teacher == null) return dto;
            dto.setId(teacher.getAisId());
            dto.setAisId(teacher.getAisId());
            dto.setName(teacher.getName());
            dto.setEmail(teacher.getEmail());
            dto.setInstitute(teacher.getInstitute());
            dto.setDepartment(teacher.getDepartment());
            dto.setTheses(teacher.getSupervisedTheses().stream()
                    .map(Thesis::getId)
                    .collect(Collectors.toList()));
            return dto;
        });
    }
}
