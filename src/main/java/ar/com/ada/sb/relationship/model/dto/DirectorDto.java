package ar.com.ada.sb.relationship.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DirectorDto implements Serializable {
    private Long id;
    private String name;
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
