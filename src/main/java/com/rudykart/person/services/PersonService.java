package com.rudykart.person.services;

import com.rudykart.person.dto.PersonDto;
import com.rudykart.person.dto.PersonResponse;

public interface PersonService {

    PersonResponse getAllPerson(int pageNo,int pageSize);
    PersonDto createPerson(PersonDto personDto);
    PersonDto getPersonById(Long id);
    PersonDto updatePerson(PersonDto personDto,Long id);
    void deletePerson(Long id);
    
    // @Autowired
    // private PersonRepository personRepository;
    
    // public PersonResponse findAll(int pageNo,int pageSize) {
    //     Pageable pageable = PageRequest.of(pageNo, pageSize);
    //     Page<Person> persons = personRepository.findAll(pageable);
    //     List<Person> listOfPerson = persons.getContent();
    //     List<PersonDto> content = listOfPerson.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

    //     PersonResponse personResponse = new PersonResponse();
    //     personResponse.setContent(content);
    //     personResponse.setPageNo(persons.getNumber());
    //     personResponse.setPageSize(persons.getSize());
    //     personResponse.setTotalElements(persons.getTotalElements());
    //     personResponse.setTotalPages(persons.getTotalPages());
    //     personResponse.setLast(persons.isLast());
    //     return personResponse;
    // }

    // private PersonDto mapToDto(Person person) {
    //     PersonDto personDto = new PersonDto();
    //     personDto.setId(person.getId());
    //     personDto.setName(person.getName());
    //     return personDto;
    // }

    // private Person mapToDto(PersonDto personDto) {
    //     Person person = new Person();
    //     person.setName(personDto.getName());
    //     return person;
    // }
}
