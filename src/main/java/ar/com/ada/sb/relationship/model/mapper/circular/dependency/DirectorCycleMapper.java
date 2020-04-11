package ar.com.ada.sb.relationship.model.mapper.circular.dependency;

import ar.com.ada.sb.relationship.model.dto.DirectorDto;
import ar.com.ada.sb.relationship.model.entity.Director;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {})
public interface DirectorCycleMapper extends DataCycleMapper<DirectorDto, Director>  {
    DirectorCycleMapper MAPPER = Mappers.getMapper(DirectorCycleMapper.class);
}
