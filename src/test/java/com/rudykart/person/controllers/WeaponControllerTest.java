package com.rudykart.person.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDate;
import java.util.Arrays;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rudykart.person.dto.PersonDto;
import com.rudykart.person.dto.WeaponDto;
import com.rudykart.person.models.entities.Person;
import com.rudykart.person.models.entities.Weapon;
import com.rudykart.person.services.WeaponService;

@WebMvcTest(controllers = WeaponController.class)
@AutoConfigureMockMvc(addFilters = false)
public class WeaponControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeaponService weaponService;

    @Autowired
    private ObjectMapper objectMapper;
    private Person person;
    private Weapon weapon;
    private PersonDto personDto;
    private WeaponDto weaponDto;

    @BeforeEach
    public void init() {
        person = Person.builder().name("rudy").birthDate(LocalDate.parse("2000-01-01")).build();
        personDto = PersonDto.builder().name("rudy").birthDate(LocalDate.parse("2000-01-01")).build();
        weapon = Weapon.builder().name("axe").build();
        weaponDto = WeaponDto.builder().name("axe").build();
    }

    @Test
    public void getWeaponByPersonId() throws Exception {
        Long personId = 1L;
        when(weaponService.getWeaponByPersonId(personId)).thenReturn(Arrays.asList(weaponDto));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/person/1/weapons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(weaponDto).size())));
    }

    @Test
    public void updateWeapon() throws Exception {
        Long personId = 1L;
        Long weaponId = 1L;
        when(weaponService.updateWeapon(personId, weaponId, weaponDto)).thenReturn(weaponDto);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/person/1/weapons/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(weaponDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(weaponDto.getName())));
    }


    @Test
    public void createWeapon() throws Exception {
        Long personId = 1L;
        when(weaponService.createWeapon(personId, weaponDto)).thenReturn(weaponDto);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/person/1/weapons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(weaponDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(weaponDto.getName())));
    }

    @Test
    public void getWeaponById() throws Exception {
        Long personId = 1L;
        Long weaponId = 1L;
        when(weaponService.getWeaponById(weaponId, personId)).thenReturn(weaponDto);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/person/1/weapons/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(weaponDto.getName())));
    }

    @Test
    public void deleteWeapon() throws Exception {
        Long personId = 1L;
        Long weaponId = 1L;

        doNothing().when(weaponService).deleteWeapon(personId, weaponId);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/person/1/weapons/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
