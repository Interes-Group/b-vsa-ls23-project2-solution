package sk.stuba.fei.uim.vsa.pr2.model.dto.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sk.stuba.fei.uim.vsa.pr2.model.dto.thesis.ThesisDto;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TeacherWithThesesDto extends TeacherDto {

    private List<ThesisDto> theses;

    public TeacherWithThesesDto() {
        theses = new ArrayList<>();
    }

    public TeacherWithThesesDto(Long id, Long aisId, String email) {
        super(id, aisId, email);
        theses = new ArrayList<>();
    }
}
