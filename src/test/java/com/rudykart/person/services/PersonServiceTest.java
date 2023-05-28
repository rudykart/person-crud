package com.rudykart.person.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rudykart.person.dto.PersonDto;
import com.rudykart.person.dto.PersonResponse;
import com.rudykart.person.models.entities.Person;
import com.rudykart.person.models.repositories.PersonRepository;
import com.rudykart.person.services.impl.PersonServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    
    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    public void createPerson() {
        Person person = Person.builder().name("Rudy").birthDate(LocalDate.parse("1812-12-12")).build();
        PersonDto personDto = PersonDto.builder().name("Rudy").birthDate(LocalDate.parse("1812-12-12")).build();

        when(personRepository.save(Mockito.any(Person.class))).thenReturn(person);

        PersonDto savedPerson = personService.createPerson(personDto);

        Assertions.assertThat(savedPerson).isNotNull();
    }

    @Test
    public void getAllPerson() {
        Page<Person> person = Mockito.mock(Page.class);
        when(personRepository.findAll(Mockito.any(Pageable.class))).thenReturn(person);
        PersonResponse savePerson = personService.getAllPerson(1, 10);
        Assertions.assertThat(savePerson).isNotNull();
    }

    @Test
    public void getPersonById() {
        Person person = Person.builder().name("Rudy").birthDate(LocalDate.parse("1812-12-12")).build();
        
        when(personRepository.findById(1L)).thenReturn(Optional.ofNullable(person));

        PersonDto savedPerson = personService.getPersonById(1L);

        Assertions.assertThat(savedPerson).isNotNull();
    }

    @Test
    public void updatedPerson() {
        Person person = Person.builder().name("Rudy").birthDate(LocalDate.parse("1812-12-12")).build();
        PersonDto personDto = PersonDto.builder().name("Rudy").birthDate(LocalDate.parse("1812-12-12")).build();

        when(personRepository.findById(1L)).thenReturn(Optional.ofNullable(person));
        when(personRepository.save(Mockito.any(Person.class))).thenReturn(person);

        PersonDto savedPerson = personService.updatePerson(personDto, 1L);

        Assertions.assertThat(savedPerson).isNotNull();
    }

    @Test
    public void deletePerson() {
        Person person = Person.builder().name("Rudy").birthDate(LocalDate.parse("1812-12-12")).build();
        
        when(personRepository.findById(1L)).thenReturn(Optional.ofNullable(person));
        
        assertAll(() -> personService.deletePerson(1L));
    }
    
    // @Mock
    // private PersonRepository personRepository;

    // @InjectMocks
    // private PersonService personService;

    // @BeforeEach
    // public void setup() {
    //     MockitoAnnotations.openMocks(this);
    // }

    // @Test
    // public void findAllSuccess() {
    //     // Mock data
    //     Person person1 = new Person();
    //     person1.setId(1L);
    //     person1.setName("Rudy");
    //     Person person2 = new Person();
    //     person2.setId(2L);
    //     person2.setName("Jane");
    //     List<Person> mockPersonList = new ArrayList<>();
    //     mockPersonList.add(person1);
    //     mockPersonList.add(person2);

    //     // Mock repository
    //     when(personRepository.findAll()).thenReturn(mockPersonList);

    //     // Call service method
    //     List<Person> result = personService.findAll();

    //     // Verify repository method was called
    //     verify(personRepository, times(1)).findAll();

    //     // Assertions
    //     Assertions.assertEquals(2, result.size());
    //     Assertions.assertEquals(person1, result.get(0));
    //     Assertions.assertEquals(person2, result.get(1));
    // }

    // @Test
    // public void findOneSuccess() {
    //     // Mock data
    //     Long id = 1L;
    //     Person person = new Person();
    //     when(personRepository.findById(id)).thenReturn(Optional.of(person));

    //     // Call service method
    //     Person result = personService.findOne(id);

    //     // Verify repository method was called
    //     verify(personRepository, times(1)).findById(id);

    //     // Assertions
    //     Assertions.assertNotNull(result);
    //     Assertions.assertEquals(person, result);
    // }

    // @Test
    // public void findOneFail() {
    //     // Mock data
    //     Long id = 2L;
    //     when(personRepository.findById(id)).thenReturn(Optional.empty());

    //     // Call service method
    //     Person result = personService.findOne(id);

    //     // Verify repository method was called
    //     verify(personRepository, times(1)).findById(id);

    //     // Assertions
    //     Assertions.assertNull(result);
    // }
    
    // @Test
    // public void saveSuccess() {
    //     // Mock data
    //     Person person = new Person();
    //     person.setName("Rudy");

    //     when(personRepository.save(any(Person.class))).thenReturn(person);

    //     // Call service method
    //     Person result = personService.save(person);

    //     // Assertions
    //     Assertions.assertNotNull(result);
    //     Assertions.assertEquals(person, result);
        
    //     // Verify repository method was called
    //     verify(personRepository, times(1)).save(any(Person.class));
    // }

    // @Test
    // public void updateSuccess() {
    //     // Mock data
    //     Person person = new Person();
    //     person.setId(1L);
    //     // Date updatedAtBeforeUpdate = person.getUpdatedAt();

    //     when(personRepository.save(person)).thenReturn(person);

    //     // Call service method
    //     Person result = personService.update(person);

    //     // Verify repository method was called
    //     verify(personRepository, times(1)).save(person);

    //     // Assertions
    //     Assertions.assertNotNull(result);
    //     Assertions.assertEquals(person, result);
    //     // Assertions.assertNotEquals(updatedAtBeforeUpdate, result.getUpdatedAt());
    // }

    // @Test
    // public void deleteSuccess() {
    //     // Mock data
    //     Long id = 1L;
    //     Person person = new Person();
    //     person.setId(id);
    //     when(personRepository.findById(id)).thenReturn(Optional.of(person));

    //     // Call service method
    //     personService.delete(id);

    //     // Verify repository method was called
    //     verify(personRepository, times(1)).findById(id);
    //     verify(personRepository, times(1)).delete(person);
    // }

    // @Test
    // public void deleteFail() {
    //     // Mock data
    //     Long id = 2L;
    //     when(personRepository.findById(id)).thenReturn(Optional.empty());

    //     // Call service method
    //     personService.delete(id);

    //     // Verify repository method was called
    //     verify(personRepository, times(1)).findById(id);
    //     verify(personRepository, never()).delete(any(Person.class));
    // }
}
