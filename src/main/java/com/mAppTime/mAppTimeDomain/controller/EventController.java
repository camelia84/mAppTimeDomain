package com.mAppTime.mAppTimeDomain.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.mAppTime.mAppTimeDomain.entity.Event;
import com.mAppTime.mAppTimeDomain.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    EventService eventService;

    @GetMapping("all")
    public ResponseEntity<List<Event>> findAll(){
        return new ResponseEntity<>(eventService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Event>> findEventById(@PathVariable Long id){
        return new ResponseEntity<>(eventService.findEventById(id),HttpStatus.OK);
    }
    @GetMapping("/{order}")
    public ResponseEntity<List<Event>> findEventsByOrder(){
        return new ResponseEntity<>(eventService.findEventsByOrder(),HttpStatus.OK);
    }
    @PostMapping("add")
    public ResponseEntity<Event> insertEvent(@RequestBody Event event){
        return new ResponseEntity<>(eventService.insertEvent(event),HttpStatus.CREATED);
    }
    @PatchMapping
    public ResponseEntity<Event> updateEvent (@PathVariable Long id, @RequestBody JsonPatch inEvent){
        try {
            Event event = eventService.findEventById(id).orElseThrow(RuntimeException::new);
            Event eventPatched = applyPatchToCustomer(inEvent, event);
            eventService.updateEvent(eventPatched);
            return ResponseEntity.ok(eventPatched);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll(){
        eventService.deleteAll();
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus>deleteById(@PathVariable Long id){
        eventService.deleteById(id);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    private Event applyPatchToCustomer(JsonPatch patch, Event targetEvent) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetEvent, JsonNode.class));
        return objectMapper.treeToValue(patched, Event.class);
    }

}
