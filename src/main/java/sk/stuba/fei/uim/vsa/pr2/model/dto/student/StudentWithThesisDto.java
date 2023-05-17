package sk.stuba.fei.uim.vsa.pr2.model.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sk.stuba.fei.uim.vsa.pr2.model.dto.thesis.ThesisDto;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StudentWithThesisDto extends StudentDto {

    private ThesisDto thesis;

    public StudentWithThesisDto() {
    }

    public StudentWithThesisDto(Long id, Long aisId, String email) {
        super(id, aisId, email);
    }
}
