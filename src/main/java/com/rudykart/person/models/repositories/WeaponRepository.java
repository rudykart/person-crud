package com.rudykart.person.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rudykart.person.models.entities.Weapon;

public interface WeaponRepository extends JpaRepository<Weapon,Long> {
    List<Weapon> findByPersonId(Long personId);
}
