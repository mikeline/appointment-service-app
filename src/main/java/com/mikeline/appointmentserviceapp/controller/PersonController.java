package com.mikeline.appointmentserviceapp.controller;

import com.mikeline.appointmentserviceapp.model.Meeting;
import com.mikeline.appointmentserviceapp.model.Person;
import com.mikeline.appointmentserviceapp.repo.PersonRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("unused")
@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonRepo personRepo;

    @Transactional
    @PostMapping(value = "/create")
    @ResponseBody
    public ResponseEntity<Person> create(@RequestBody Person person) {
        Optional<Person> res = Optional.ofNullable(personRepo.save(person));
        if(res.isPresent()) {
            return new ResponseEntity<>(res.get(), CREATED);
        }
        else {
            return new ResponseEntity<>(CONFLICT);
        }
    }

    @Transactional
    @PostMapping(value = "/delete/{email}")
    public ResponseEntity delete(@PathVariable String email) {
        if(personRepo.existsByEmail(email)) {
            Person p = personRepo.findByEmail(email).get(0);
            for(Meeting m : p.getMeetingsAttending()) {
                p.deleteMeeting(m);
            }
            personRepo.delete(p);
            return new ResponseEntity(OK);
        }
        else {
            return new ResponseEntity(NOT_FOUND);
        }
    }
}
