package sk.stuba.fei.uim.vsa.pr2.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlTransient;

@Data
@XmlTransient
@NoArgsConstructor
@AllArgsConstructor
public abstract class Dto {

    private Long id;

}
