package ar.com.ada.sb.relationship.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name= "Director")
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String bio;

    @OneToMany(mappedBy = "director")
    private Set<Film> films;

    public Director(Long id) {
        this.id = id;
    }

    public Director(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }
}
