package ar.com.ada.sb.relationship.component.data;

import ar.com.ada.sb.relationship.model.entity.Film;
import ar.com.ada.sb.relationship.model.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class FilmLoaderData implements ApplicationRunner {

    @Autowired @Qualifier("filmRepository")
    private FilmRepository filmRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Film> filmList = Arrays.asList(
                new Film("","",""),
                new Film("","",""),
                new Film("","",""),
                new Film("","",""),
                new Film("","",""),
        );
        filmList.forEach(film -> filmRepository.save(film));
    }
}
