package com.mikeline.appointmentserviceapp.controller;

import com.mikeline.appointmentserviceapp.model.Meeting;
import com.mikeline.appointmentserviceapp.repo.MeetingRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("unused")
@Controller
public class HomeController {

    private final MeetingRepo meetingRepo;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/new-meeting")
    public String newMeeting() {
        return "newMeeting";
    }

    @RequestMapping("/add-participant")
    public String addParticipant() {
        return "addParticipant";
    }

    @RequestMapping("/new-person")
    public String newPerson() {
        return "newPerson";
    }

    @RequestMapping("/delete-person")
    public String deletePerson() {
        return "deletePerson";
    }

    @RequestMapping("/cancel-meeting")
    public String deleteMeeting() {
        return "deleteMeeting";
    }

    @RequestMapping("/delete-participant")
    public String deleteParticipant() {
        return "deleteParticipant";
    }

    @RequestMapping("/show-all-meetings")
    public ModelAndView showAllMeetings() {
        ModelAndView result = new ModelAndView();
        List<Meeting> meetings = meetingRepo.findAll();
        result.addObject("meetings", meetings);
        result.setViewName("showAllMeetings");
        return result;
    }
}
