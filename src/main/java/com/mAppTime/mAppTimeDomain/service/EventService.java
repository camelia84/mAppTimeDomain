package com.mAppTime.mAppTimeDomain.service;

import com.mAppTime.mAppTimeDomain.dao.EventRepository;
import com.mAppTime.mAppTimeDomain.dao.UserRepository;
import com.mAppTime.mAppTimeDomain.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserRepository userRepository;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public List<Event> findEventsByOrder() {
        return eventRepository.findEventsByOrder();
    }

    public Optional<Event> findEventById(Long id) {
        return eventRepository.findById(id);
    }

    public List<Event> findEventByDate(Long dtstart, Long dtEnd) {
        return eventRepository.findEventByDate(dtstart, dtEnd);
    }

    public Event insertEvent(Event event) {
        return eventRepository.save(event);
    }

    public ResponseEntity<Event> updateEvent(Event inEvent) {
        try {
            Event newEvent = eventRepository.findById(inEvent.getEventId()).orElseThrow(RuntimeException::new);
            newEvent.setDescription(inEvent.getDescription());
            newEvent.setTitle(inEvent.getTitle());
            newEvent.setDtEnd(inEvent.getDtEnd());
            newEvent.setDtStart(inEvent.getDtStart());
            newEvent.setJsonData(inEvent.getJsonData());
            newEvent.setUsers(inEvent.getUsers());
            eventRepository.save(newEvent);
            return ResponseEntity.ok(newEvent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    public void deleteAll(){
         eventRepository.deleteAll();
    }
    public void deleteById(Long id){
        eventRepository.deleteById(id);
    }




}
