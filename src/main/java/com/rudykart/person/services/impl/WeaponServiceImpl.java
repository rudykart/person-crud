package com.rudykart.person.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rudykart.person.dto.WeaponDto;
import com.rudykart.person.exceptions.PersonNotFoundException;
import com.rudykart.person.exceptions.WeaponNotFoundException;
import com.rudykart.person.models.entities.Person;
import com.rudykart.person.models.entities.Weapon;
import com.rudykart.person.models.repositories.PersonRepository;
import com.rudykart.person.models.repositories.WeaponRepository;
import com.rudykart.person.services.WeaponService;

@Service
public class WeaponServiceImpl implements WeaponService {

    private WeaponRepository weaponRepository;
    private PersonRepository personRepository;

    @Autowired
    public WeaponServiceImpl(WeaponRepository weaponRepository, PersonRepository personRepository) {
        this.weaponRepository = weaponRepository;
        this.personRepository = personRepository;
    }

    @Override
    public List<WeaponDto> getWeaponByPersonId(Long id) {

        List<Weapon> weapons = weaponRepository.findByPersonId(id);
        return weapons.stream().map(w -> mapToDto(w)).collect(Collectors.toList());
    }

    @Override
    public WeaponDto createWeapon(Long personId, WeaponDto weaponDto) {

        Weapon weapon = mapToEntity(weaponDto);

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotFoundException("Person not found"));

        weapon.setPerson(person);

        Weapon newWeapon = weaponRepository.save(weapon);

        return mapToDto(newWeapon);
    }

    @Override
    public WeaponDto getWeaponById(Long weaponId, Long personId) {

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotFoundException("Person not found"));
        Weapon weapon = weaponRepository.findById(weaponId)
                .orElseThrow(() -> new WeaponNotFoundException("Weapon not found"));

        if (weapon.getPerson().getId() != person.getId()) {
            throw new WeaponNotFoundException("Senjata tidak ada");
        }

        return mapToDto(weapon);
    }

    @Override
    public WeaponDto updateWeapon(Long personId, Long weaponId, WeaponDto weaponDto) {

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotFoundException("Person not found"));
        Weapon weapon = weaponRepository.findById(weaponId)
                .orElseThrow(() -> new WeaponNotFoundException("Weapon not found"));

        if (weapon.getPerson().getId() != person.getId()) {
            throw new WeaponNotFoundException("Senjata tidak ada");
        }

        weapon.setName(weaponDto.getName());
        Weapon updateWeapon = weaponRepository.save(weapon);

        return mapToDto(updateWeapon);
    }

    @Override
    public void deleteWeapon(Long personId, Long weaponId) {

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotFoundException("Person not found"));
        Weapon weapon = weaponRepository.findById(weaponId)
                .orElseThrow(() -> new WeaponNotFoundException("Weapon not found"));

        if (weapon.getPerson().getId() != person.getId()) {
            throw new WeaponNotFoundException("Senjata tidak ada");
        }

        weaponRepository.delete(weapon);
    }

    public WeaponDto mapToDto(Weapon weapon) {
        WeaponDto weaponDto = new WeaponDto();
        weaponDto.setId(weapon.getId());
        weaponDto.setName(weapon.getName());
        weaponDto.setCreatedAt(weapon.getCreatedAt());
        weaponDto.setUpdatedAt(weapon.getUpdatedAt());
        return weaponDto;
    }

    public Weapon mapToEntity(WeaponDto weaponDto) {
        Weapon weapon = new Weapon();
        weapon.setId(weaponDto.getId());
        weapon.setName(weaponDto.getName());
        weapon.setCreatedAt(weaponDto.getCreatedAt());
        weapon.setUpdatedAt(weaponDto.getUpdatedAt());
        return weapon;
    }
}
