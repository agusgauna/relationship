package ar.com.ada.sb.relationship.model.repository;

import ar.com.ada.sb.relationship.model.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("actorRepository")
public interface ActorRepository extends JpaRepository<Actor, Long> {

    // Optional<Actor> findByNameAndGender(String name, String gender);
}
