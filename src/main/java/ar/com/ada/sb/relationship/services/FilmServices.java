package ar.com.ada.sb.relationship.services;

import ar.com.ada.sb.relationship.component.BusinessLogicExceptionComponent;
import ar.com.ada.sb.relationship.exception.ApiEntityError;
import ar.com.ada.sb.relationship.exception.BusinessLogicException;
import ar.com.ada.sb.relationship.model.dto.FilmDto;
import ar.com.ada.sb.relationship.model.entity.Actor;
import ar.com.ada.sb.relationship.model.entity.Film;
import ar.com.ada.sb.relationship.model.mapper.FilmMapper;
import ar.com.ada.sb.relationship.model.repository.ActorRepository;
import ar.com.ada.sb.relationship.model.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service ("filmServices")
public class FilmServices implements Services<FilmDto> {

    @Autowired @Qualifier("businessLogicExceptionComponent")
    private BusinessLogicExceptionComponent logicExceptionComponent;

    @Autowired @Qualifier("filmRepository")
    private FilmRepository filmRepository;

    @Autowired @Qualifier("actorRepository")
    private ActorRepository actorRepository;

    private FilmMapper filmMapper;

    public FilmServices(FilmMapper filmMapper) {
        this.filmMapper = filmMapper;
    }

    @Override
    public List<FilmDto> findAll() {
        List<Film> filmEntityList = filmRepository.findAll();
        List<FilmDto> filmDtoList = filmMapper.toDto(filmEntityList);
        return filmDtoList;
    }

    public FilmDto findFilmById(Long id) {
        //SELECT * FROM Film WHERE id =
        Optional<Film> byIdOptional = filmRepository.findById(id);
        FilmDto filmDto= null;

        if(byIdOptional.isPresent()) {
            Film filmById = byIdOptional.get();
            filmDto = filmMapper.toDto(filmById);
        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Film", id);
        }
        return filmDto;
    }

    @Override
    public FilmDto save(FilmDto dto) {
        Film filmToSave = filmMapper.toEntity(dto);
        Film filmSaved = filmRepository.save(filmToSave);
        FilmDto filmDtoSaved = filmMapper.toDto(filmSaved);
        return filmDtoSaved;
    }

    public FilmDto updateFilm (FilmDto filmDtoToUpdate, Long id){
        Optional<Film> byIdOptional = filmRepository.findById(id);
        FilmDto filmDtoUpdated = null;

        if(byIdOptional.isPresent()) {
            Film filmById = byIdOptional.get();
            filmDtoToUpdate.setId(filmById.getId());
            Film filmToUpdate = filmMapper.toEntity(filmDtoToUpdate);
            Film filmUpdated = filmRepository.save(filmToUpdate);
            filmDtoUpdated = filmMapper.toDto(filmUpdated);

        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Film", id);
        }
        return filmDtoUpdated;
    }

    @Override
    public void delete(Long id) {
        Optional<Film> byIdOptional = filmRepository.findById(id);
        if (byIdOptional.isPresent()){
            Film filmToDelete = byIdOptional.get();
            filmRepository.delete(filmToDelete);
        }  else {
            logicExceptionComponent.throwExceptionEntityNotFound("Film", id);
        }
    }

    public FilmDto addActorToFilm(Long actorId, Long filmId) {
        Optional<Film> filmByIdOptional = filmRepository.findById(filmId);
        Optional<Actor> actorByIdOptional = actorRepository.findById(actorId);
        FilmDto filmDtoWithNewActor = null;

        if (!filmByIdOptional.isPresent())
            logicExceptionComponent.throwExceptionEntityNotFound("Film",filmId);
        if (!actorByIdOptional.isPresent())
            logicExceptionComponent.throwExceptionEntityNotFound("Actor",actorId);

        Film film = filmByIdOptional.get();
        Actor actorToAdd = actorByIdOptional.get();

        boolean hasActorInFilm = film.getActors()
                .stream()
                .anyMatch(actor -> actor.getName().equals(actorToAdd.getName()));

        if (!hasActorInFilm){
            film.addActor(actorToAdd);
            Film filmWithNewActor = filmRepository.save(film);
            filmDtoWithNewActor = filmMapper.toDto(filmWithNewActor);
        } else {
            ApiEntityError apiEntityError = new ApiEntityError(
                    "Actor",
                    "AlreadyExist",
                    "The actor with id " + actorId + "already exist in the film"
            );
            throw new BusinessLogicException(
                    "Actor already exist in the film",
                    HttpStatus.BAD_REQUEST,
                    apiEntityError
            );
        }
        return filmDtoWithNewActor;
    }
}
