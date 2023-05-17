package sk.stuba.fei.uim.vsa.pr2.model.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sk.stuba.fei.uim.vsa.pr2.model.dto.UserDto;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateStudentDto extends UserDto {

    @NotNull
    private Long aisId;

    @NotNull
    private String name;
    private Integer year;
    private Integer term;
    private String programme;

    public CreateStudentDto() {
    }

    public CreateStudentDto(Long aisId, String name, String email, String password) {
        super(email, password);
        this.aisId = aisId;
        this.name = name;
    }
}
