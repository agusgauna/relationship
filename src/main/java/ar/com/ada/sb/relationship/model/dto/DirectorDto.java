package ar.com.ada.sb.relationship.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DirectorDto implements Serializable {
    private Long id;

    @NotBlank( message = "name is required")
    private String name;

    @NotBlank( message = "bio is required")
    private String bio;

    @JsonIgnoreProperties(value = "director")
    private List<FilmDto> films;

}
