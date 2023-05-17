package sk.stuba.fei.uim.vsa.pr2.model.dto.teacher;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sk.stuba.fei.uim.vsa.pr2.model.dto.UserDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateTeacherDto extends UserDto {

    private Long aisId;
    private String name;
    private String institute;
    private String department;

    public CreateTeacherDto() {
    }

    public CreateTeacherDto(Long aisId, String name, String email, String password) {
        super(email, password);
        this.aisId = aisId;
        this.name = name;
    }
}
