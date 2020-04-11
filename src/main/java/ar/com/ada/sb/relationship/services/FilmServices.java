package ar.com.ada.sb.relationship.services;

import ar.com.ada.sb.relationship.component.BusinessLogicExceptionComponent;
import ar.com.ada.sb.relationship.exception.ApiEntityError;
import ar.com.ada.sb.relationship.exception.BusinessLogicException;
import ar.com.ada.sb.relationship.model.dto.FilmDto;
import ar.com.ada.sb.relationship.model.entity.Actor;
import ar.com.ada.sb.relationship.model.entity.Film;
import ar.com.ada.sb.relationship.model.mapper.circular.dependency.CycleAvoidingMappingContext;
import ar.com.ada.sb.relationship.model.mapper.circular.dependency.FilmCycleMapper;
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

    private FilmCycleMapper filmCycleMapper = FilmCycleMapper.MAPPER;

    @Autowired @Qualifier("cycleAvoidingMappingContext")
    private CycleAvoidingMappingContext context;

    @Override
    public List<FilmDto> findAll() {
        List<Film> filmEntityList = filmRepository.findAll();
        List<FilmDto> filmDtoList = filmCycleMapper.toDto(filmEntityList, context);
        return filmDtoList;
    }

    public FilmDto findFilmById(Long id) {
        //SELECT * FROM Film WHERE id =
        Optional<Film> byIdOptional = filmRepository.findById(id);
        FilmDto filmDto= null;

        if(byIdOptional.isPresent()) {
            Film filmById = byIdOptional.get();
            filmDto = filmCycleMapper.toDto(filmById, context);
        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Film", id);
        }
        return filmDto;
    }

    @Override
    public FilmDto save(FilmDto dto) {
        Film filmToSave = filmCycleMapper.toEntity(dto, context);
        Film filmSaved = filmRepository.save(filmToSave);
        FilmDto filmDtoSaved = filmCycleMapper.toDto(filmSaved, context);
        return filmDtoSaved;
    }

    public FilmDto updateFilm (FilmDto filmDtoToUpdate, Long id){
        Optional<Film> byIdOptional = filmRepository.findById(id);
        FilmDto filmDtoUpdated = null;

        if(byIdOptional.isPresent()) {
            Film filmById = byIdOptional.get();
            filmDtoToUpdate.setId(filmById.getId());
            Film filmToUpdate = filmCycleMapper.toEntity(filmDtoToUpdate, context);
            Film filmUpdated = filmRepository.save(filmToUpdate);
            filmDtoUpdated = filmCycleMapper.toDto(filmUpdated, context);

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
            filmDtoWithNewActor = filmCycleMapper.toDto(filmWithNewActor, context);
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
