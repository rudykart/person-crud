package com.rudykart.person.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.rudykart.person.dto.WeaponDto;
import com.rudykart.person.services.WeaponService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class WeaponController {

    private WeaponService weaponService;
    
    @Autowired
    public WeaponController(WeaponService weaponService) {
        this.weaponService = weaponService;
    }

    @GetMapping("/person/{personId}/weapons")
    public List<WeaponDto> getWeaponByPersonId(@PathVariable(value = "personId") Long personId) {
        return weaponService.getWeaponByPersonId(personId);
    }

    @PostMapping("/person/{personId}/weapons")
    public ResponseEntity<WeaponDto> createWeapon(@PathVariable(value = "personId") Long personId,
            @Valid @RequestBody WeaponDto weaponDto) {
        return new ResponseEntity<>(weaponService.createWeapon(personId, weaponDto), HttpStatus.CREATED);
    }

    @GetMapping("/person/{personId}/weapons/{id}")
    public ResponseEntity<WeaponDto> getWeaponById(@PathVariable(value = "personId") Long personId,
            @PathVariable(value = "id") Long weaponId) {
        WeaponDto weaponDto = weaponService.getWeaponById( weaponId,personId);
        return new ResponseEntity<>(weaponDto, HttpStatus.OK);
    }

    @PutMapping("/person/{personId}/weapons/{id}")
    public ResponseEntity<WeaponDto> updateWeapon(@PathVariable(value = "personId") Long personId,
            @PathVariable(value = "id") Long weaponId,
            @Valid @RequestBody WeaponDto weaponDto) {
        WeaponDto updatedReview = weaponService.updateWeapon(personId, weaponId, weaponDto);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/person/{personId}/weapons/{id}")
    public ResponseEntity<String> deleteWeapon(@PathVariable(value = "personId") Long personId,
            @PathVariable(value = "id") Long weaponId) {
        weaponService.deleteWeapon(personId, weaponId);
        return new ResponseEntity<>("Weapon deleted successfully", HttpStatus.OK);
    }
}
