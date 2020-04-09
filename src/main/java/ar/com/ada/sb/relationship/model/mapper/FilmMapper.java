package ar.com.ada.sb.relationship.model.mapper;


import ar.com.ada.sb.relationship.model.dto.FilmDto;
import ar.com.ada.sb.relationship.model.entity.Film;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface FilmMapper extends DataMapper<Film, FilmDto> {

    Film toEntity(FilmDto dto);

    FilmDto toDto(Film entity);

    default Film fromId (Long id) {
        if (id == null) return null;
        return new Film(id);
    }
}
