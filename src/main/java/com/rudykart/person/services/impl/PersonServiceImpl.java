package com.rudykart.person.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rudykart.person.dto.PersonDto;
import com.rudykart.person.dto.PersonResponse;
import com.rudykart.person.exceptions.PersonNotFoundException;
import com.rudykart.person.models.entities.Person;
import com.rudykart.person.models.repositories.PersonRepository;
import com.rudykart.person.services.PersonService;

@Service
public class PersonServiceImpl implements PersonService {
    private PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public PersonResponse getAllPerson(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Person> persons = personRepository.findAll(pageable);
        List<Person> listOfPerson = persons.getContent();
        List<PersonDto> content = listOfPerson.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

        PersonResponse personResponse = new PersonResponse();
        personResponse.setContent(content);
        personResponse.setPageNo(persons.getNumber());
        personResponse.setPageSize(persons.getSize());
        personResponse.setTotalElements(persons.getTotalElements());
        personResponse.setTotalPages(persons.getTotalPages());
        personResponse.setLast(persons.isLast());
        return personResponse;
    }

    @Override
    public PersonDto createPerson(PersonDto personDto) {
    
        Person person = new Person();
        person.setName(personDto.getName());
        person.setBirthDate(personDto.getBirthDate());

        Person savedPerson = personRepository.save(person);

        PersonDto personResponse = new PersonDto();
        personResponse.setId(savedPerson.getId());
        personResponse.setName(savedPerson.getName());
        personResponse.setBirthDate(savedPerson.getBirthDate());
        personResponse.setCreatedAt(savedPerson.getCreatedAt());
        personResponse.setUpdatedAt(savedPerson.getUpdatedAt());
        return personResponse;
    }

    @Override
    public PersonDto getPersonById(Long id) {
    
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person not found"));
        return mapToDto(person);
    }

    @Override
    public PersonDto updatePerson(PersonDto personDto, Long id) {
    
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person not found"));

        person.setName(personDto.getName());
        person.setBirthDate(personDto.getBirthDate());

        Person updatedPerson = personRepository.save(person);
        return mapToDto(updatedPerson);
    }

    @Override
    public void deletePerson(Long id) {
    
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person not found"));
        personRepository.delete(person);
    }

    private PersonDto mapToDto(Person person) {
        PersonDto personDto = new PersonDto();
        personDto.setId(person.getId());
        personDto.setName(person.getName());
        personDto.setBirthDate(person.getBirthDate());
        personDto.setAge(person.getAge());
        personDto.setCreatedAt(person.getCreatedAt());
        personDto.setUpdatedAt(person.getUpdatedAt());
        return personDto;
    }

    // private Person mapToEntity(PersonDto personDto) {
    //     Person person = new Person();
    //     person.setName(personDto.getName());
    //     return person;
    // }
}
