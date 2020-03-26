package ar.com.ada.sb.relationship.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ActorDto {
    private Long id;
    private String name;
    private String gender;
    private Date birthday;
    private String bio;
    //Set para validar una sola vez ese actor, que no este repetido
    private Set<FilmDto> films;

    public ActorDto(Long id, String name, String gender, Date birthday, String bio, Set<FilmDto> films) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.bio = bio;
        this.films = films;
    }

    public ActorDto(String name, String gender, Date birthday, String bio, Set<FilmDto> films) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.bio = bio;
        this.films = films;
    }

}
