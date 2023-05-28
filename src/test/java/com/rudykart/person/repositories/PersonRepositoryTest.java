package com.rudykart.person.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.rudykart.person.models.entities.Person;
import com.rudykart.person.models.repositories.PersonRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Test
    public void save() {
        //Arrange
        Person person =Person.builder()
        .name("Rudy")
        .birthDate(LocalDate.parse("1921-12-03"))
        .build();

        //Act
        Person savePerson = personRepository.save(person);

        //Assert
        Assertions.assertThat(savePerson).isNotNull();
        Assertions.assertThat(savePerson.getId()).isGreaterThan(0);
        Assertions.assertThat(savePerson.getCreatedAt()).isNotNull();
        Assertions.assertThat(savePerson.getUpdatedAt()).isNotNull();
    }

    @Test
    public void saveBatch() {
        //Arrange
        Person person1 =Person.builder()
        .name("Rudy").birthDate(LocalDate.parse("1921-12-03")).build();

        Person person2 =Person.builder()
        .name("Rudy").birthDate(LocalDate.parse("1921-12-03")).build();

        //Act
        personRepository.save(person1);
        personRepository.save(person2);

        List<Person> data = personRepository.findAll();

        //Assert
        Assertions.assertThat(data).isNotNull();
        Assertions.assertThat(data.size()).isEqualTo(2);
    }
    
    @Test
    public void findOne() {
        //Arrange
        Person person =Person.builder()
        .name("Rudy").birthDate(LocalDate.parse("1921-12-03")).build();

        //Act
        personRepository.save(person);

        Person data = personRepository.findById(person.getId()).get();

        //Assert
        Assertions.assertThat(data).isNotNull();
    }
    
    @Test
    public void findByName() {
        //Arrange
        Person person =Person.builder()
        .name("Tejo").birthDate(LocalDate.parse("1921-12-03")).build();

        //Act
        personRepository.save(person);
        Person personEdit = personRepository.findById(person.getId()).get();
        personEdit.setName("rudy");

        Person personUpdate = personRepository.save(personEdit);

        List<Person> data = personRepository.findByNameContaining("ru");

        //Assert
        Assertions.assertThat(data.size()).isEqualTo(1);
        Assertions.assertThat(personUpdate.getName()).isEqualTo("rudy");
    }

    @Test
    public void delete() {
        //Arrange
        Person person =Person.builder()
        .name("Tejo").birthDate(LocalDate.parse("1921-12-03")).build();

        //Act
        personRepository.save(person);
        
        personRepository.deleteById(person.getId());
        Optional<Person> data = personRepository.findById(person.getId());
        
        //Assert
        Assertions.assertThat(data).isEmpty();
    }
}
