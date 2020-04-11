package ar.com.ada.sb.relationship.services;

import ar.com.ada.sb.relationship.component.BusinessLogicExceptionComponent;
import ar.com.ada.sb.relationship.model.dto.ActorDto;
import ar.com.ada.sb.relationship.model.entity.Actor;
import ar.com.ada.sb.relationship.model.mapper.ActorMapper;
import ar.com.ada.sb.relationship.model.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("actorServices")
public class ActorServices implements Services<ActorDto>{

    @Autowired @Qualifier("businessLogicExceptionComponent")
    private BusinessLogicExceptionComponent logicExceptionComponent;

    @Autowired @Qualifier("actorRepository")
    private ActorRepository actorRepository;

    private ActorMapper actorMapper;

    public ActorServices(ActorMapper actorMapper) {
        this.actorMapper = actorMapper;
    }

    @Override
    public List<ActorDto> findAll() {
        List<Actor> actorEntityList = actorRepository.findAll();
        List<ActorDto> actorDtoList = actorMapper.toDto(actorEntityList);
        return actorDtoList;
    }

    public ActorDto findActorById(Long id) {
        //SELECT * FROM Actor WHERE id = 
        Optional<Actor> byIdOptional = actorRepository.findById(id);
        ActorDto actorDto= null;

        if(byIdOptional.isPresent()) {
            Actor actorById = byIdOptional.get();
            actorDto = actorMapper.toDto(actorById);
        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Actor", id);
        }
        return actorDto;
    }

    @Override
    public ActorDto save(ActorDto dto) {
        Actor actorToSave = actorMapper.toEntity(dto);
        Actor actorSaved = actorRepository.save(actorToSave);
        ActorDto actorDtoSaved = actorMapper.toDto(actorSaved);
        return actorDtoSaved;
    }

    public ActorDto updateActor(ActorDto actorDtoToUpdate, Long id){
        Optional<Actor> byIdOptional = actorRepository.findById(id);
        ActorDto actorDtoUpdated = null;

        if(byIdOptional.isPresent()) {
            Actor actorById = byIdOptional.get();
            actorDtoToUpdate.setId(actorById.getId());
            Actor actorToUpdate = actorMapper.toEntity(actorDtoToUpdate);
            Actor actorUpdated = actorRepository.save(actorToUpdate);
            actorDtoUpdated = actorMapper.toDto(actorUpdated);

        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Actor", id);
        }
        return actorDtoUpdated;
    }

    @Override
    public void delete(Long id) {
        Optional<Actor> byIdOptional = actorRepository.findById(id);
        if (byIdOptional.isPresent()){
            Actor actorToDelete = byIdOptional.get();
            actorRepository.delete(actorToDelete);
        }  else {
            logicExceptionComponent.throwExceptionEntityNotFound("Actor", id);
        }
    }

}
