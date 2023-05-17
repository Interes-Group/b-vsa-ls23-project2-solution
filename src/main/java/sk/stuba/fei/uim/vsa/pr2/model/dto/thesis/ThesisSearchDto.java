package sk.stuba.fei.uim.vsa.pr2.model.dto.thesis;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sk.stuba.fei.uim.vsa.pr2.model.dto.UserIdDto;
import sk.stuba.fei.uim.vsa.pr2.model.entity.ThesisStatus;
import sk.stuba.fei.uim.vsa.pr2.model.entity.ThesisType;

import java.time.LocalDate;


@Data
@EqualsAndHashCode(callSuper = true)
public class ThesisSearchDto extends UserIdDto {

    private String department;
    private String publishedOn;
    private String type;
    private String status;

    public ThesisSearchDto() {
        super();
    }

    public LocalDate getPublishedOnAsLocalDate() {
        if (this.publishedOn == null || this.publishedOn.isEmpty()) return null;
        return LocalDate.parse(this.publishedOn);
    }

    public ThesisType getTypeAsThesisType() {
        return ThesisType.valueOf(type.toUpperCase());
    }

    public ThesisStatus getStatusAsThesisStatus() {
        return ThesisStatus.valueOf(status.toUpperCase());
    }
}
