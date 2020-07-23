package com.mikeline.appointmentserviceapp.repo;

import com.mikeline.appointmentserviceapp.model.Meeting;
import org.hibernate.mapping.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MeetingRepo extends JpaRepository<Meeting, UUID> {

    void deleteByTitle(String title);

    boolean existsByTitle(String title);

    List<Meeting> findByTitle(String title);

}
