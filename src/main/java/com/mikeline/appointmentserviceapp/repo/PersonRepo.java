package com.mikeline.appointmentserviceapp.repo;

import com.mikeline.appointmentserviceapp.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PersonRepo extends JpaRepository<Person, UUID> {

    void deleteByEmail(String email);

    boolean existsByEmail(String title);

    List<Person> findByEmail(String email);
}
