package sk.stuba.fei.uim.vsa.pr2.model.dto.student;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sk.stuba.fei.uim.vsa.pr2.model.dto.Dto;

import javax.xml.bind.annotation.XmlTransient;


@Data
@XmlTransient
@EqualsAndHashCode(callSuper = true)
public abstract class StudentDto extends Dto {

    private Long aisId;
    private String name;
    private String email;
    private Integer year;
    private Integer term;
    private String programme;

    public StudentDto() {
    }

    public StudentDto(Long id, Long aisId, String email) {
        super(id);
        this.aisId = aisId;
        this.email = email;
    }
}
