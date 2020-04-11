package ar.com.ada.sb.relationship.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ActorDto implements Serializable {
    private Long id;

    @NotBlank( message = "name is required")
    private String name;

    @NotBlank(message = "gender is required")
    private String gender;

    @NotNull(message = "birthday is required")
    @Past(message = "the birth must be past data")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @JsonIgnoreProperties(value = "actors")
    private Set<FilmDto> films;

    public ActorDto(Long id, String name, String gender, LocalDate birthday, Set<FilmDto> films) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.films = films;
    }

    public ActorDto(String name, String gender, LocalDate birthday, Set<FilmDto> films) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.films = films;
    }

}
