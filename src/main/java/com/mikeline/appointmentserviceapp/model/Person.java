package com.mikeline.appointmentserviceapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Table(name = "person")
@ToString
public class Person {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    private String phone;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    private String occupation;

    @JsonIgnore
    @ManyToMany(mappedBy = "participants")
    @ToString.Exclude private Set<Meeting> meetingsAttending = new HashSet<>();


    public void deleteMeeting(Meeting m) {
        this.meetingsAttending.remove(m);
        m.getParticipants().remove(this);
    }


}
