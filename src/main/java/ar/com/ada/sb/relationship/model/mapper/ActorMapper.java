package ar.com.ada.sb.relationship.model.mapper;

import ar.com.ada.sb.relationship.model.dto.ActorDto;
import ar.com.ada.sb.relationship.model.entity.Actor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ActorMapper extends DataMapper<Actor, ActorDto> {

    Actor toEntity(ActorDto dto);

    ActorDto toDto(Actor entity);

    default Actor fromId (Long id) {
        if (id == null) return null;
        return new Actor(id);
    }
}

