package sk.stuba.fei.uim.vsa.pr2.model.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StudentAltDto extends StudentDto {

    private Long thesis;

    public StudentAltDto() {
    }

    public StudentAltDto(Long id, Long aisId, String email) {
        super(id, aisId, email);
    }
}
