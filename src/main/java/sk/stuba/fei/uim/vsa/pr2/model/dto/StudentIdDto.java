package sk.stuba.fei.uim.vsa.pr2.model.dto;

import lombok.Data;

@Data
public class StudentIdDto {

    private Long studentId;

    public StudentIdDto() {
    }

    public StudentIdDto(Long studentId) {
        this.studentId = studentId;
    }
}
