package sk.stuba.fei.uim.vsa.pr2.model.dto.thesis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import sk.stuba.fei.uim.vsa.pr2.model.dto.Dto;
import sk.stuba.fei.uim.vsa.pr2.model.dto.student.StudentAltDto;
import sk.stuba.fei.uim.vsa.pr2.model.dto.teacher.TeacherAltDto;
import sk.stuba.fei.uim.vsa.pr2.model.entity.ThesisType;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ThesisDto extends Dto {

    private String registrationNumber;
    private String title;
    private String description;
    private String department;
    private TeacherAltDto supervisor;
    private StudentAltDto author;
    private LocalDate publishedOn;
    private LocalDate deadline;
    private ThesisType type;
    private Status status;

    public ThesisDto() {
    }

    public ThesisDto(Long id, String registrationNumber, String title, String department, TeacherAltDto supervisor, ThesisType type) {
        super(id);
        this.registrationNumber = registrationNumber;
        this.title = title;
        this.department = department;
        this.supervisor = supervisor;
        this.type = type;
    }


    public static enum Status {
        FREE_TO_TAKE,
        IN_PROGRESS,
        SUBMITTED
    }

}
