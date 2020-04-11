package ar.com.ada.sb.relationship.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class FilmDto implements Serializable {
    private Long id;

    @NotBlank( message = "title is required")
    private String title;

    @NotBlank( message = "description is required")
    private String description;

    @JsonIgnoreProperties(value = "films")
    private DirectorDto director;

    @JsonIgnoreProperties(value = "films")
    private Set<ActorDto> actors;

    public FilmDto(Long id, String title, String description, DirectorDto director, Set<ActorDto> actors) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.director = director;
        this.actors = actors;
    }

    public FilmDto(String title, String description, DirectorDto director, Set<ActorDto> actors) {
        this.title = title;
        this.description = description;
        this.director = director;
        this.actors = actors;
    }

}
