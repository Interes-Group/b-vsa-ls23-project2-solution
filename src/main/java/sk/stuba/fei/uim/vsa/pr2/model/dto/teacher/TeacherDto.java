package sk.stuba.fei.uim.vsa.pr2.model.dto.teacher;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sk.stuba.fei.uim.vsa.pr2.model.dto.Dto;

import javax.xml.bind.annotation.XmlTransient;

@Data
@XmlTransient
@EqualsAndHashCode(callSuper = true)
public abstract class TeacherDto extends Dto {

    private Long aisId;
    private String name;
    private String email;
    private String institute;
    private String department;

    public TeacherDto() {
    }

    public TeacherDto(Long id, Long aisId, String email) {
        super(id);
        this.aisId = aisId;
        this.email = email;
    }
}
