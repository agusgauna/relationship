package ar.com.ada.sb.relationship.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
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

    @Column(nullable = false, columnDefinition = "YEAR")
    private Date year;

    @ManyToOne
    @JoinColumn(name = "Director_id", nullable = false)
    private Director director;

    @ManyToMany
    @JoinTable(name = "Actor_has_Film",
            joinColumns = @JoinColumn(name = "Film_id"),
            inverseJoinColumns = @JoinColumn(name = "Actor_id"))
    private Set<Actor> actors;

}
