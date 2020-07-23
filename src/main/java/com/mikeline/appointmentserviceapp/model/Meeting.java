package com.mikeline.appointmentserviceapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Table(name = "meeting")
public class Meeting {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    private String topic;

    private String description;

    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @ManyToMany
    @JoinTable(name = "meeting_participant",
               joinColumns = @JoinColumn(name = "meeting_id"),
               inverseJoinColumns = @JoinColumn(name = "participant_id"))
    @EqualsAndHashCode.Exclude private Set<Person> participants = new HashSet<>();

    public void addParticipant(Person p) {
        this.participants.add(p);
        p.getMeetingsAttending().add(this);
    }

    public void deleteParticipant(Person p) {
        this.participants.remove(p);
        p.getMeetingsAttending().remove(this);
    }

}
