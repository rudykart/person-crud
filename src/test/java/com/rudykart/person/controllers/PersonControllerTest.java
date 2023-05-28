package com.rudykart.person.controllers;

import java.time.LocalDate;
import java.util.Arrays;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rudykart.person.dto.PersonDto;
import com.rudykart.person.dto.PersonResponse;
import com.rudykart.person.dto.WeaponDto;
import com.rudykart.person.models.entities.Person;
import com.rudykart.person.models.entities.Weapon;
import com.rudykart.person.services.PersonService;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = PersonController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

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
    public void createPerson() throws Exception {

        given(personService.createPerson(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/person/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(personDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birth_date",
                        CoreMatchers.is(personDto.getBirthDate().toString())));
    }

    @Test
    public void getAllPerson() throws Exception {
        PersonResponse responseDto = PersonResponse.builder().pageSize(10).last(true).pageNo(1)
                .content(Arrays.asList(personDto)).build();
        when(personService.getAllPerson(1, 10)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/api/person")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", "1")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()",
                        CoreMatchers.is(responseDto.getContent().size())));
    }

    @Test
    public void getPersonById() throws Exception {
        Long personId = 1L;
        when(personService.getPersonById(personId)).thenReturn(personDto);

        ResultActions response = mockMvc.perform(get("/api/person/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(personDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birth_date",
                        CoreMatchers.is(personDto.getBirthDate().toString())));
    }

    @Test
    public void updatePerson() throws Exception {
        Long personId = 1L;
        when(personService.updatePerson(personDto, personId)).thenReturn(personDto);

        ResultActions response = mockMvc.perform(put("/api/person/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(personDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birth_date",
                        CoreMatchers.is(personDto.getBirthDate().toString())));
    }

    @Test
    public void deletePerson() throws Exception {
        Long personId = 1L;
        doNothing().when(personService).deletePerson(personId);

        ResultActions response = mockMvc.perform(delete("/api/person/1/delete")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    // @Mock
    // private PersonService personService;

    // @Mock
    // private ModelMapper modelMapper;

    // @InjectMocks
    // private PersonController personController;

    // @BeforeEach
    // public void setup() {
    // MockitoAnnotations.openMocks(this);
    // }

    // @Test
    // public void testGetAll() {
    // // Mock data
    // List<Person> persons = new ArrayList<>();
    // persons.add(new Person(1L, "John", "123 Main St", "Male", null, null, null,
    // null));

    // // Mock dependencies
    // when(personService.findAll()).thenReturn(persons);

    // // Call controller method
    // ResponseEntity<ResponseData<List<Person>>> response =
    // personController.getAll();

    // // Verify service method was called
    // verify(personService, times(1)).findAll();

    // // Assertions
    // assertEquals(HttpStatus.OK, response.getStatusCode());
    // assertNotNull(response.getBody());
    // // assertEquals(persons.size(), response.getBody().getPayload().size());
    // }

    // @Test
    // public void testCreatePerson() {
    // // Mock data
    // PersonDTO personDTO = new PersonDTO("John", "123 Main St", "Male", null,
    // null);
    // Person person = new Person(1L, "John",null, null);

    // // Mock dependencies
    // when(modelMapper.map(any(PersonDTO.class),
    // eq(Person.class))).thenReturn(person);
    // when(personService.save(any(Person.class))).thenReturn(person);

    // // Call controller method
    // ResponseEntity<ResponseData<Person>> response =
    // personController.create(personDTO);

    // // Verify dependencies were called
    // verify(modelMapper, times(1)).map(personDTO, Person.class);
    // verify(personService, times(1)).save(person);

    // // Assertions
    // assertEquals(HttpStatus.CREATED, response.getStatusCode());
    // assertNotNull(response.getBody());
    // // assertEquals(person.getId(), response.getBody().getPayload().getId());
    // // assertEquals(person.getName(), response.getBody().getPayload().getName());
    // // Add more assertions for other properties
    // }
}
