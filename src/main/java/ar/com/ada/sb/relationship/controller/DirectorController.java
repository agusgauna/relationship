package ar.com.ada.sb.relationship.controller;

import ar.com.ada.sb.relationship.model.dto.DirectorDto;
import ar.com.ada.sb.relationship.services.DirectorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/directors")
public class DirectorController {

    @Autowired @Qualifier("directorServices")
    private DirectorServices directorServices;

    @GetMapping({"","/"})
    public ResponseEntity getAllDirectors() {
        List<DirectorDto> all = directorServices.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping({"/{id}","/{id}/"})
    public ResponseEntity getDirectorById(@PathVariable Long id){
        DirectorDto directorById = directorServices.findDirectorById(id);
        return ResponseEntity.ok(directorById);
    }

    @PostMapping({"","/"})
    public ResponseEntity addNewDirector(@Valid @RequestBody DirectorDto directorDto) {
        DirectorDto directorSaved = directorServices.save(directorDto);
        return ResponseEntity.ok(directorSaved);
    }

    @PutMapping({"/{id}","/{id}/"})
    public ResponseEntity updateDirectorById(@Valid @RequestBody DirectorDto directorDto, @PathVariable Long id){
        DirectorDto directorUpdated = directorServices.updateDirector(directorDto, id);
        return ResponseEntity.ok(directorUpdated);
    }

    @DeleteMapping({"/{id}","/{id}/"})
    public ResponseEntity deleteDirector(@PathVariable Long id){
        directorServices.delete(id);
        return ResponseEntity.noContent().build();
    }

}
