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
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String bio;

    @OneToMany(mappedBy = "director")
    private Set<Film> films;
}
