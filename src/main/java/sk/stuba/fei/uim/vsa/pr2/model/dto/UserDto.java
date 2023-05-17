package sk.stuba.fei.uim.vsa.pr2.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

@Data
@XmlTransient
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserDto {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
