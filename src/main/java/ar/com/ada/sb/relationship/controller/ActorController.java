package ar.com.ada.sb.relationship.controller;

import ar.com.ada.sb.relationship.model.dto.ActorDto;
import ar.com.ada.sb.relationship.services.ActorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/actors")
public class ActorController {

    @Autowired @Qualifier("actorServices")
    private ActorServices actorServices;

    @GetMapping({"","/"})
    public ResponseEntity getAllActors() {
        List<ActorDto> all = actorServices.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping({"/{id}","/{id}/"})
    public ResponseEntity getActorById(@PathVariable Long id){
        ActorDto actorById = actorServices.findActorById(id);
        return ResponseEntity.ok(actorById);
    }

    @PostMapping({"","/"})
    public ResponseEntity addNewActor(@Valid @RequestBody ActorDto actorDto) {
        ActorDto actorSaved = actorServices.save(actorDto);
        return ResponseEntity.ok(actorSaved);
    }

    @PutMapping({"/{id}","/{id}/"})
    public ResponseEntity updateActorById(@Valid @RequestBody ActorDto actorDto, @PathVariable Long id){
        ActorDto actorUpdated = actorServices.updateActor(actorDto, id);
        return ResponseEntity.ok(actorUpdated);
    }

    @DeleteMapping({"/{id}","/{id}/"})
    public ResponseEntity deleteActor(@PathVariable Long id){
        actorServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}

