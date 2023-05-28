package com.rudykart.person.services;

import java.util.List;

import com.rudykart.person.dto.WeaponDto;

public interface WeaponService {
    List<WeaponDto> getWeaponByPersonId(Long id);
    WeaponDto createWeapon(Long personId, WeaponDto weaponDto);
    WeaponDto getWeaponById(Long weaponId, Long personId);
    WeaponDto updateWeapon(Long personId, Long weaponId, WeaponDto weaponDto);
    void deleteWeapon(Long personId, Long weaponId);
}
