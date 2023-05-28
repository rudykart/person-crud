package com.rudykart.person.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rudykart.person.dto.PersonDto;
import com.rudykart.person.dto.PersonResponse;
import com.rudykart.person.services.PersonService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class PersonController {

    private PersonService personService;
    
    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/person")
    public ResponseEntity<PersonResponse> getAllPersons(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return new ResponseEntity<>(personService.getAllPerson(pageNo, pageSize), HttpStatus.OK);
    }

    @PostMapping("/person/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PersonDto> createPokemon(@Valid @RequestBody PersonDto personDto) {
        return new ResponseEntity<>(personService.createPerson(personDto), HttpStatus.CREATED);
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<PersonDto> pokemonDetail(@PathVariable Long id) {
        return ResponseEntity.ok(personService.getPersonById(id));

    }

    @PutMapping("/person/{id}/update")
    public ResponseEntity<PersonDto> updatePokemon(@Valid @RequestBody PersonDto personDto, @PathVariable("id") Long pokemonId) {
        PersonDto response = personService.updatePerson(personDto, pokemonId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/person/{id}/delete")
    public ResponseEntity<String> deletePokemon(@PathVariable("id") Long personId) {
        personService.deletePerson(personId);
        return new ResponseEntity<>("Person has delete", HttpStatus.OK);
    }
}
