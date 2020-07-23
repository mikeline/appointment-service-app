package com.mikeline.appointmentserviceapp.controller;

import com.mikeline.appointmentserviceapp.model.Meeting;
import com.mikeline.appointmentserviceapp.model.ParticipantDto;
import com.mikeline.appointmentserviceapp.model.Person;
import com.mikeline.appointmentserviceapp.repo.MeetingRepo;
import com.mikeline.appointmentserviceapp.repo.PersonRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("unused")
@RestController
@RequestMapping("/meeting")
public class MeetingController {

    private final MeetingRepo meetingRepo;
    private final PersonRepo personRepo;

    @Transactional
    @PostMapping(value = "/create")
    @ResponseBody
    public ResponseEntity<Meeting> create(@RequestBody Meeting meeting) {
        Optional<Meeting> res = Optional.ofNullable(meetingRepo.save(meeting));
        if(res.isPresent()) {
            return new ResponseEntity<>(res.get(), CREATED);
        }
        else {
            return new ResponseEntity<>(CONFLICT);
        }

    }

    @Transactional
    @PostMapping(value = "/delete/{title}")
    public ResponseEntity delete(@PathVariable String title) {
        if(meetingRepo.existsByTitle(title)) {
            meetingRepo.deleteByTitle(title);
            return new ResponseEntity(OK);
        }
        else {
            return new ResponseEntity(NOT_FOUND);
        }
    }

    @Transactional
    @PostMapping(value = "/add-participant")
    @ResponseBody
    public ResponseEntity addParticipant(@RequestBody ParticipantDto participantDto) {
        Optional<List<Meeting>> optionalMeetings = Optional.ofNullable(meetingRepo.findByTitle(participantDto.getTitle()));
        Optional<List<Person>> optionalPersons = Optional.ofNullable(personRepo.findByEmail(participantDto.getEmail()));
        if(optionalMeetings.isPresent() && optionalPersons.isPresent()) {
            Meeting meeting = optionalMeetings.get().get(0);
            Person participant = optionalPersons.get().get(0);
            String isBusy = isBusy(participant, meeting.getStartTime(), meeting.getEndTime()) ? "Busy" : "Free";
            meeting.addParticipant(participant);
            meetingRepo.save(meeting);
            return new ResponseEntity(isBusy, OK);
        }
        else {
            return new ResponseEntity(NOT_FOUND);
        }
    }

    private boolean isBusy(Person p, LocalDateTime startTime, LocalDateTime endTime) {
        for(Meeting m : p.getMeetingsAttending()) {
            if(m.getStartTime().isBefore(endTime) && m.getStartTime().isAfter(startTime)) {
                return true;
            }
            if(m.getEndTime().isBefore(endTime) && m.getStartTime().isAfter(startTime)) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    @PostMapping(value = "/delete-participant")
    @ResponseBody
    public ResponseEntity deleteParticipant(@RequestBody ParticipantDto participantDto) {
        Optional<List<Meeting>> optionalMeetings = Optional.ofNullable(meetingRepo.findByTitle(participantDto.getTitle()));
        Optional<List<Person>> optionalPersons = Optional.ofNullable(personRepo.findByEmail(participantDto.getEmail()));
        if(optionalMeetings.isPresent() && optionalPersons.isPresent()) {
            Meeting meeting = optionalMeetings.get().get(0);
            Person participant = optionalPersons.get().get(0);
            meeting.deleteParticipant(participant);
            meetingRepo.save(meeting);
            return new ResponseEntity(OK);
        }
        else {
            return new ResponseEntity(NOT_FOUND);
        }
    }

    @PostMapping(value = "/all")
    @ResponseBody
    public ResponseEntity<Collection<Meeting>> selectAll() {
        return new ResponseEntity<>(meetingRepo.findAll(), OK);
    }
}
