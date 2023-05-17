package sk.stuba.fei.uim.vsa.pr2.model.dto;

import lombok.Data;

@Data
public class UserIdDto {

    private Long studentId;
    private Long teacherId;

    public UserIdDto() {
    }

}
