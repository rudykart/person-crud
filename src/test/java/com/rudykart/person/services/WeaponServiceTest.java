package com.rudykart.person.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rudykart.person.dto.PersonDto;
import com.rudykart.person.dto.WeaponDto;
import com.rudykart.person.models.entities.Person;
import com.rudykart.person.models.entities.Weapon;
import com.rudykart.person.models.repositories.PersonRepository;
import com.rudykart.person.models.repositories.WeaponRepository;
import com.rudykart.person.services.impl.WeaponServiceImpl;

@ExtendWith(MockitoExtension.class)
public class WeaponServiceTest {

    @Mock
    private WeaponRepository weaponRepository;
    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private WeaponServiceImpl weaponService;

    private Person person;
    private Weapon  weapon;
    private PersonDto personDto;
    private WeaponDto weaponDto;

    @BeforeEach
    public void init() {
        person = Person.builder().name("rudy").birthDate(LocalDate.parse("1818-11-21")).build();
        personDto= PersonDto.builder().name("rudy").birthDate(LocalDate.parse("1818-11-21")).build();
        weapon= Weapon.builder().name("knife").build();
        weaponDto= WeaponDto.builder().name("knife").build();
    }

    @Test
    public void createWeapon() {
        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
        when(weaponRepository.save(Mockito.any(Weapon.class))).thenReturn(weapon);

        WeaponDto savedWeapon = weaponService.createWeapon(person.getId(), weaponDto);

        Assertions.assertThat(savedWeapon).isNotNull();
    }

    @Test
    public void getWeaponByPersonId() {
        Long weaponId = 1L;
        when(weaponRepository.findByPersonId(weaponId)).thenReturn(Arrays.asList(weapon));

        List<WeaponDto> pokemonReturn = weaponService.getWeaponByPersonId(weaponId);

        Assertions.assertThat(pokemonReturn).isNotNull();
    }

    @Test
    public void getWeaponById() {
        Long weaponId = 1L;
        Long personId = 1L;

        weapon.setPerson(person);

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(weaponRepository.findById(weaponId)).thenReturn(Optional.of(weapon));

        WeaponDto weaponReturn = weaponService.getWeaponById(weaponId, personId);

        Assertions.assertThat(weaponReturn).isNotNull();
        Assertions.assertThat(weaponReturn).isNotNull();
    }

    @Test
    public void updateWeapon() {
        Long personId = 1L;
        Long weaponId = 1L;

        person.setWeapons(Arrays.asList(weapon));
        weapon.setPerson(person);

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(weaponRepository.findById(weaponId)).thenReturn(Optional.of(weapon));
        when(weaponRepository.save(weapon)).thenReturn(weapon);

        WeaponDto updateReturn = weaponService.updateWeapon(personId, weaponId, weaponDto);

        Assertions.assertThat(updateReturn).isNotNull();
    }

    @Test
    public void deleteWeapon() {
        Long personId = 1L;
        Long weaponId = 1L;

        person.setWeapons(Arrays.asList(weapon));
        weapon.setPerson(person);

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(weaponRepository.findById(weaponId)).thenReturn(Optional.of(weapon));

        assertAll(() -> weaponService.deleteWeapon(personId, weaponId));
    }

}
