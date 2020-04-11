package ar.com.ada.sb.relationship.component.data;

import ar.com.ada.sb.relationship.model.entity.Actor;
import ar.com.ada.sb.relationship.model.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class ActorLoaderData implements ApplicationRunner {

    @Autowired @Qualifier
    private ActorRepository actorRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Actor> actorList = Arrays.asList(
                new Actor("Scarlett Johansson","F", LocalDate.parse("1984-11-21")),
                new Actor("Jennifer Aniston","F", LocalDate.parse("1969-02-10")),
                new Actor("Brad Pitt","M", LocalDate.parse("1963-12-17")),
                new Actor("Leonardo Dicaprio","M", LocalDate.parse("1974-11-10"))
        );
        actorList.forEach(actor -> actorRepository.save(actor));

//        for (Actor actor: actorList){
//            actorRepository.save(actor);
//        }
//
//        for (int i = 0; 1 < actorList.size(); i++) {
//            Actor actor = actorList.get(i);
//            actorRepository.save(actor);
//        }
    }
}
