package ar.com.ada.sb.relationship.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
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

    private Set<FilmDto> films;

    public DirectorDto(Long id, String name, String bio, Set<FilmDto> films) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.films = films;
    }

    public DirectorDto(String name, String bio, Set<FilmDto> films) {
        this.name = name;
        this.bio = bio;
        this.films = films;
    }
}
