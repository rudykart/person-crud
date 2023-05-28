package com.rudykart.person.repositories;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.rudykart.person.models.entities.Weapon;
import com.rudykart.person.models.repositories.WeaponRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class WeaponRepositoryTest {
    private WeaponRepository weaponRepository;
    
    @Autowired
    public WeaponRepositoryTest(WeaponRepository weaponRepository) {
        this.weaponRepository=weaponRepository;
    }
    
    @Test
    public void save() {
        Weapon weapon = Weapon.builder().name("Knife").build();
        
        Weapon savedWeapon = weaponRepository.save(weapon);
        
        Assertions.assertThat(savedWeapon).isNotNull();
        Assertions.assertThat(savedWeapon.getId()).isGreaterThan(0);
        Assertions.assertThat(savedWeapon.getName()).isEqualTo("Knife");
    }
    
    @Test
    public void getAll() {
        Weapon weapon = Weapon.builder().name("Knife").build();
        Weapon weapon2 = Weapon.builder().name("Gun").build();
        
        weaponRepository.save(weapon);
        weaponRepository.save(weapon2);
        
        List<Weapon> data = weaponRepository.findAll();
        
        Assertions.assertThat(data).isNotNull();
        Assertions.assertThat(data.size()).isEqualTo(2);
    }
    
    @Test
    public void findById() {
        Weapon weapon = Weapon.builder().name("Shotgun").build();

        weaponRepository.save(weapon);
        Weapon getWeapon = weaponRepository.findById(weapon.getId()).get();

        Assertions.assertThat(getWeapon.getName()).isEqualTo("Shotgun");

    }
    
    @Test
    public void update() {
        Weapon weapon = Weapon.builder().name("Knife").build();

        weaponRepository.save(weapon);
        Weapon getWeapon = weaponRepository.findById(weapon.getId()).get();
        getWeapon.setName("Axe");
        Weapon updatWeapon = weaponRepository.save(getWeapon);

        Assertions.assertThat(updatWeapon.getId()).isEqualTo(weapon.getId());
        Assertions.assertThat(updatWeapon.getName()).isEqualTo("Axe");
        
    }
    
    @Test
    public void delete() {
        Weapon weapon = Weapon.builder().name("Axe").build();
        weaponRepository.save(weapon);
        weaponRepository.deleteById(weapon.getId());

        Optional<Weapon> deletedWeapon = weaponRepository.findById(weapon.getId());

        Assertions.assertThat(deletedWeapon).isEmpty();
    }
}
            