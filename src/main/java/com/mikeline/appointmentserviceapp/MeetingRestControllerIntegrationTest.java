package com.mikeline.appointmentserviceapp;


import com.mikeline.appointmentserviceapp.model.Meeting;
import com.mikeline.appointmentserviceapp.repo.MeetingRepo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class MeetingRestControllerIntegrationTest {

    @Mock
    private MeetingRepo repo;

    @Test
    public void testSampleRepoCreateNewMeeting() {
        Meeting meeting = new Meeting();
        meeting.setTitle("How gase emmisions can affect the air");
        meeting.setTopic("Ecology");
        meeting.setPlace("Cafe Brest");
        String startTimeStr = "2020-08-20 12:30";
        String endTimeStr = "2020-08-20 14:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr, formatter);
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr, formatter);
        meeting.setStartTime(startTime);
        meeting.setEndTime(endTime);

        when(repo.save(meeting)).thenReturn(meeting);

        if(meeting != null) {
            assertThat(repo.save(meeting), instanceOf(Meeting.class));
            assertNotNull("Title is not null", meeting.getTitle());
            assertNotNull("Place is not null", meeting.getPlace());
        }

        assertNotNull("New meeting is not null", meeting);
    }


    @Test
    public void testSampleRepoGetMeeting() {

        Optional<Meeting> existingMeetingOptional = repo.findById(UUID.fromString("b790ebeb-cf47-4fe9-976f-3b29ef5112dc"));

        if (existingMeetingOptional.isPresent()) {
            Meeting existingMeeting = existingMeetingOptional.get();
            assertThat(repo.findById(UUID.fromString("b790ebeb-cf47-4fe9-976f-3b29ef5112dc")), instanceOf(Meeting.class));
            assertNotNull("Title is not null", existingMeeting.getTitle());
            assertNotNull("Place is not null", existingMeeting.getPlace());
            assertNotNull("New meeting is not null", existingMeeting);
        }


    }
}
