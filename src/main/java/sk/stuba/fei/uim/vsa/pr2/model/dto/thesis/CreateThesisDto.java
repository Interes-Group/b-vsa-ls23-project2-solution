package sk.stuba.fei.uim.vsa.pr2.model.dto.thesis;

import lombok.AllArgsConstructor;
import lombok.Data;
import sk.stuba.fei.uim.vsa.pr2.model.entity.ThesisType;

@Data
@AllArgsConstructor
public class CreateThesisDto {

    private String registrationNumber;
    private String title;
    private String description;
    private ThesisType type;

    public CreateThesisDto() {
    }

    public CreateThesisDto(String registrationNumber, String title, ThesisType type) {
        this.registrationNumber = registrationNumber;
        this.title = title;
        this.type = type;
    }
}
