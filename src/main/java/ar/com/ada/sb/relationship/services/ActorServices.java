package ar.com.ada.sb.relationship.services;

import ar.com.ada.sb.relationship.component.BusinessLogicExceptionComponent;
import ar.com.ada.sb.relationship.model.dto.ActorDto;
import ar.com.ada.sb.relationship.model.entity.Actor;
import ar.com.ada.sb.relationship.model.mapper.circular.dependency.ActorCycleMapper;
import ar.com.ada.sb.relationship.model.mapper.circular.dependency.CycleAvoidingMappingContext;
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

    private ActorCycleMapper actorCycleMapper = ActorCycleMapper.MAPPER;

    @Autowired @Qualifier("cycleAvoidingMappingContext")
    private CycleAvoidingMappingContext context;

    @Override
    public List<ActorDto> findAll() {
        List<Actor> actorEntityList = actorRepository.findAll();
        List<ActorDto> actorDtoList = actorCycleMapper.toDto(actorEntityList, context);
        return actorDtoList;
    }

    public ActorDto findActorById(Long id) {
        //SELECT * FROM Actor WHERE id = 
        Optional<Actor> byIdOptional = actorRepository.findById(id);
        ActorDto actorDto= null;

        if(byIdOptional.isPresent()) {
            Actor actorById = byIdOptional.get();
            actorDto = actorCycleMapper.toDto(actorById, context);
        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Actor", id);
        }
        return actorDto;
    }

    @Override
    public ActorDto save(ActorDto dto) {
        Actor actorToSave = actorCycleMapper.toEntity(dto, context);
        Actor actorSaved = actorRepository.save(actorToSave);
        ActorDto actorDtoSaved = actorCycleMapper.toDto(actorSaved, context);
        return actorDtoSaved;
    }

    public ActorDto updateActor(ActorDto actorDtoToUpdate, Long id){
        Optional<Actor> byIdOptional = actorRepository.findById(id);
        ActorDto actorDtoUpdated = null;

        if(byIdOptional.isPresent()) {
            Actor actorById = byIdOptional.get();
            actorDtoToUpdate.setId(actorById.getId());
            Actor actorToUpdate = actorCycleMapper.toEntity(actorDtoToUpdate, context);
            Actor actorUpdated = actorRepository.save(actorToUpdate);
            actorDtoUpdated = actorCycleMapper.toDto(actorUpdated, context);

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
