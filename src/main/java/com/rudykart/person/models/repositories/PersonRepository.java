package com.rudykart.person.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rudykart.person.models.entities.Person;

public interface PersonRepository extends JpaRepository<Person,Long>{
    List<Person> findByNameContaining(String name);
}
