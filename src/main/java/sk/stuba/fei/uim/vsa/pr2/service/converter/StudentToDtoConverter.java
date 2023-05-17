package sk.stuba.fei.uim.vsa.pr2.service.converter;

import sk.stuba.fei.uim.vsa.pr2.model.dto.student.StudentAltDto;
import sk.stuba.fei.uim.vsa.pr2.model.dto.student.StudentWithThesisDto;
import sk.stuba.fei.uim.vsa.pr2.model.dto.thesis.ThesisDto;
import sk.stuba.fei.uim.vsa.pr2.model.entity.Student;
import sk.stuba.fei.uim.vsa.pr2.model.entity.Thesis;

public class StudentToDtoConverter extends EntityToDtoConverter<Student> {

    public StudentToDtoConverter() {
        super();
        addConverter(StudentWithThesisDto.class, student -> {
            StudentWithThesisDto dto = new StudentWithThesisDto();
            if (student == null) return dto;
            dto.setId(student.getAisId());
            dto.setAisId(student.getAisId());
            dto.setName(student.getName());
            dto.setEmail(student.getEmail());
            dto.setYear(student.getYear());
            dto.setTerm(student.getTerm());
            dto.setProgramme(student.getStudyProgramme());
            dto.setThesis(EntityToDtoConverterFactory.forEntity(Thesis.class).convert(student.getThesis(), ThesisDto.class));
            return dto;
        });
        addConverter(StudentAltDto.class, student -> {
            StudentAltDto dto = new StudentAltDto();
            if (student == null) return dto;
            dto.setId(student.getAisId());
            dto.setAisId(student.getAisId());
            dto.setName(student.getName());
            dto.setEmail(student.getEmail());
            dto.setYear(student.getYear());
            dto.setTerm(student.getTerm());
            dto.setProgramme(student.getStudyProgramme());
            if (student.getThesis() != null) {
                dto.setThesis(student.getThesis().getId());
            }
            return dto;
        });
    }
}
