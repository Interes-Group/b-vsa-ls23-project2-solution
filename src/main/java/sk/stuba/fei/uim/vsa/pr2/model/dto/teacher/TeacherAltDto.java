package sk.stuba.fei.uim.vsa.pr2.model.dto.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TeacherAltDto extends TeacherDto {

    private List<Long> theses;

    public TeacherAltDto() {
    }

    public TeacherAltDto(Long id, Long aisId, String email) {
        super(id, aisId, email);
    }
}
