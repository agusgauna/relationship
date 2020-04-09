package ar.com.ada.sb.relationship.services;

import ar.com.ada.sb.relationship.exception.ApiEntityError;
import ar.com.ada.sb.relationship.exception.BusinessLogicException;
import ar.com.ada.sb.relationship.model.dto.DirectorDto;
import ar.com.ada.sb.relationship.model.entity.Director;
import ar.com.ada.sb.relationship.model.mapper.DirectorMapper;
import ar.com.ada.sb.relationship.model.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service ("directorServices")
public class DirectorServices implements Services<DirectorDto>{

    @Autowired @Qualifier("actorRepository")
    private DirectorRepository directorRepository;

    private DirectorMapper directorMapper;

    public DirectorServices(DirectorMapper directorMapper) {
        this.directorMapper = directorMapper;
    }

    @Override
    public List<DirectorDto> findAll() {
        List<Director> directorEntityList = directorRepository.findAll();
        List<DirectorDto> directorDtoList = directorMapper.toDto(directorEntityList);
        return directorDtoList;
    }

    public DirectorDto findDirectorById(Long id) {
        //SELECT * FROM Director WHERE id =
        Optional<Director> byIdOptional = directorRepository.findById(id);
        DirectorDto directorDto = null;

        if(byIdOptional.isPresent()) {
            Director directorById = byIdOptional.get();
            directorDto = directorMapper.toDto(directorById);
        } else {
            throwBusinessLogicException(id);
        }
        return directorDto;
    }

    @Override
    public DirectorDto save(DirectorDto dto) {
        Director directorToSave = directorMapper.toEntity(dto);
        Director directorSaved = directorRepository.save(directorToSave);
        DirectorDto directorDtoSaved = directorMapper.toDto(directorSaved);
        return directorDtoSaved;
    }

    public DirectorDto updateDirector(DirectorDto directorDtoToUpdate, Long id){
        Optional<Director> byIdOptional = directorRepository.findById(id);
        DirectorDto directorDtoUpdated = null;

        if(byIdOptional.isPresent()) {
            Director directorById = byIdOptional.get();
            directorDtoToUpdate.setId(directorById.getId());
            Director directorToUpdate = directorMapper.toEntity(directorDtoToUpdate);
            Director directorUpdated = directorRepository.save(directorToUpdate);
            directorDtoUpdated = directorMapper.toDto(directorUpdated);

        } else {
            throwBusinessLogicException(id);
        }
        return directorDtoUpdated;
    }

    @Override
    public void delete(Long id) {
        Optional<Director> byIdOptional = directorRepository.findById(id);
        if (byIdOptional.isPresent()){
            Director directorToDelete = byIdOptional.get();
            directorRepository.delete(directorToDelete);
        }  else {
            throwBusinessLogicException(id);
        }
    }
    private void throwBusinessLogicException(Long id) {
        ApiEntityError apiEntityError = new ApiEntityError(
                "Director",
                "NotFound",
                "The director with id " + id + "does not exist"
        );
        throw new BusinessLogicException(
                "The director not exist",
                HttpStatus.NOT_FOUND,
                apiEntityError
        );
    }
}
