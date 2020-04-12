package ar.com.ada.sb.relationship.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Film")
public class Film {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "Director_id", nullable = true)
    private Director director;

    @ManyToMany
    @JoinTable(name = "Actor_has_Film",
            joinColumns = @JoinColumn(name = "Film_id"),
            inverseJoinColumns = @JoinColumn(name = "Actor_id"))
    private Set<Actor> actors;

    public Film(Long id) {
        this.id = id;
    }

    public Film(String title, String description) {
        this.title = title;
        this.description = description;
    }
    public void addActor(Actor actor){
        this.actors.add(actor);
    }
}
