package com.eeu.smaartu.web.rest;

import com.eeu.smaartu.SmaartuApp;

import com.eeu.smaartu.domain.ControlSystem;
import com.eeu.smaartu.repository.ControlSystemRepository;
import com.eeu.smaartu.service.ControlSystemService;
import com.eeu.smaartu.service.dto.ControlSystemDTO;
import com.eeu.smaartu.service.mapper.ControlSystemMapper;
import com.eeu.smaartu.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ControlSystemResource REST controller.
 *
 * @see ControlSystemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmaartuApp.class)
public class ControlSystemResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private ControlSystemRepository controlSystemRepository;

    @Autowired
    private ControlSystemMapper controlSystemMapper;

    @Autowired
    private ControlSystemService controlSystemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restControlSystemMockMvc;

    private ControlSystem controlSystem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ControlSystemResource controlSystemResource = new ControlSystemResource(controlSystemService);
        this.restControlSystemMockMvc = MockMvcBuilders.standaloneSetup(controlSystemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControlSystem createEntity(EntityManager em) {
        ControlSystem controlSystem = new ControlSystem()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE);
        return controlSystem;
    }

    @Before
    public void initTest() {
        controlSystem = createEntity(em);
    }

    @Test
    @Transactional
    public void createControlSystem() throws Exception {
        int databaseSizeBeforeCreate = controlSystemRepository.findAll().size();

        // Create the ControlSystem
        ControlSystemDTO controlSystemDTO = controlSystemMapper.toDto(controlSystem);
        restControlSystemMockMvc.perform(post("/api/control-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(controlSystemDTO)))
            .andExpect(status().isCreated());

        // Validate the ControlSystem in the database
        List<ControlSystem> controlSystemList = controlSystemRepository.findAll();
        assertThat(controlSystemList).hasSize(databaseSizeBeforeCreate + 1);
        ControlSystem testControlSystem = controlSystemList.get(controlSystemList.size() - 1);
        assertThat(testControlSystem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testControlSystem.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createControlSystemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = controlSystemRepository.findAll().size();

        // Create the ControlSystem with an existing ID
        controlSystem.setId(1L);
        ControlSystemDTO controlSystemDTO = controlSystemMapper.toDto(controlSystem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restControlSystemMockMvc.perform(post("/api/control-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(controlSystemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ControlSystem> controlSystemList = controlSystemRepository.findAll();
        assertThat(controlSystemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllControlSystems() throws Exception {
        // Initialize the database
        controlSystemRepository.saveAndFlush(controlSystem);

        // Get all the controlSystemList
        restControlSystemMockMvc.perform(get("/api/control-systems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(controlSystem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getControlSystem() throws Exception {
        // Initialize the database
        controlSystemRepository.saveAndFlush(controlSystem);

        // Get the controlSystem
        restControlSystemMockMvc.perform(get("/api/control-systems/{id}", controlSystem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(controlSystem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingControlSystem() throws Exception {
        // Get the controlSystem
        restControlSystemMockMvc.perform(get("/api/control-systems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateControlSystem() throws Exception {
        // Initialize the database
        controlSystemRepository.saveAndFlush(controlSystem);
        int databaseSizeBeforeUpdate = controlSystemRepository.findAll().size();

        // Update the controlSystem
        ControlSystem updatedControlSystem = controlSystemRepository.findOne(controlSystem.getId());
        updatedControlSystem
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE);
        ControlSystemDTO controlSystemDTO = controlSystemMapper.toDto(updatedControlSystem);

        restControlSystemMockMvc.perform(put("/api/control-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(controlSystemDTO)))
            .andExpect(status().isOk());

        // Validate the ControlSystem in the database
        List<ControlSystem> controlSystemList = controlSystemRepository.findAll();
        assertThat(controlSystemList).hasSize(databaseSizeBeforeUpdate);
        ControlSystem testControlSystem = controlSystemList.get(controlSystemList.size() - 1);
        assertThat(testControlSystem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testControlSystem.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingControlSystem() throws Exception {
        int databaseSizeBeforeUpdate = controlSystemRepository.findAll().size();

        // Create the ControlSystem
        ControlSystemDTO controlSystemDTO = controlSystemMapper.toDto(controlSystem);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restControlSystemMockMvc.perform(put("/api/control-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(controlSystemDTO)))
            .andExpect(status().isCreated());

        // Validate the ControlSystem in the database
        List<ControlSystem> controlSystemList = controlSystemRepository.findAll();
        assertThat(controlSystemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteControlSystem() throws Exception {
        // Initialize the database
        controlSystemRepository.saveAndFlush(controlSystem);
        int databaseSizeBeforeDelete = controlSystemRepository.findAll().size();

        // Get the controlSystem
        restControlSystemMockMvc.perform(delete("/api/control-systems/{id}", controlSystem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ControlSystem> controlSystemList = controlSystemRepository.findAll();
        assertThat(controlSystemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ControlSystem.class);
        ControlSystem controlSystem1 = new ControlSystem();
        controlSystem1.setId(1L);
        ControlSystem controlSystem2 = new ControlSystem();
        controlSystem2.setId(controlSystem1.getId());
        assertThat(controlSystem1).isEqualTo(controlSystem2);
        controlSystem2.setId(2L);
        assertThat(controlSystem1).isNotEqualTo(controlSystem2);
        controlSystem1.setId(null);
        assertThat(controlSystem1).isNotEqualTo(controlSystem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ControlSystemDTO.class);
        ControlSystemDTO controlSystemDTO1 = new ControlSystemDTO();
        controlSystemDTO1.setId(1L);
        ControlSystemDTO controlSystemDTO2 = new ControlSystemDTO();
        assertThat(controlSystemDTO1).isNotEqualTo(controlSystemDTO2);
        controlSystemDTO2.setId(controlSystemDTO1.getId());
        assertThat(controlSystemDTO1).isEqualTo(controlSystemDTO2);
        controlSystemDTO2.setId(2L);
        assertThat(controlSystemDTO1).isNotEqualTo(controlSystemDTO2);
        controlSystemDTO1.setId(null);
        assertThat(controlSystemDTO1).isNotEqualTo(controlSystemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(controlSystemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(controlSystemMapper.fromId(null)).isNull();
    }
}
