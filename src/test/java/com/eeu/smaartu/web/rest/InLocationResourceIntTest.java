package com.eeu.smaartu.web.rest;

import com.eeu.smaartu.SmaartuApp;

import com.eeu.smaartu.domain.InLocation;
import com.eeu.smaartu.repository.InLocationRepository;
import com.eeu.smaartu.service.InLocationService;
import com.eeu.smaartu.service.dto.InLocationDTO;
import com.eeu.smaartu.service.mapper.InLocationMapper;
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
 * Test class for the InLocationResource REST controller.
 *
 * @see InLocationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmaartuApp.class)
public class InLocationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private InLocationRepository inLocationRepository;

    @Autowired
    private InLocationMapper inLocationMapper;

    @Autowired
    private InLocationService inLocationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInLocationMockMvc;

    private InLocation inLocation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InLocationResource inLocationResource = new InLocationResource(inLocationService);
        this.restInLocationMockMvc = MockMvcBuilders.standaloneSetup(inLocationResource)
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
    public static InLocation createEntity(EntityManager em) {
        InLocation inLocation = new InLocation()
            .name(DEFAULT_NAME)
            .detail(DEFAULT_DETAIL)
            .address(DEFAULT_ADDRESS);
        return inLocation;
    }

    @Before
    public void initTest() {
        inLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createInLocation() throws Exception {
        int databaseSizeBeforeCreate = inLocationRepository.findAll().size();

        // Create the InLocation
        InLocationDTO inLocationDTO = inLocationMapper.toDto(inLocation);
        restInLocationMockMvc.perform(post("/api/in-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inLocationDTO)))
            .andExpect(status().isCreated());

        // Validate the InLocation in the database
        List<InLocation> inLocationList = inLocationRepository.findAll();
        assertThat(inLocationList).hasSize(databaseSizeBeforeCreate + 1);
        InLocation testInLocation = inLocationList.get(inLocationList.size() - 1);
        assertThat(testInLocation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInLocation.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testInLocation.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createInLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inLocationRepository.findAll().size();

        // Create the InLocation with an existing ID
        inLocation.setId(1L);
        InLocationDTO inLocationDTO = inLocationMapper.toDto(inLocation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInLocationMockMvc.perform(post("/api/in-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<InLocation> inLocationList = inLocationRepository.findAll();
        assertThat(inLocationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInLocations() throws Exception {
        // Initialize the database
        inLocationRepository.saveAndFlush(inLocation);

        // Get all the inLocationList
        restInLocationMockMvc.perform(get("/api/in-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())));
    }

    @Test
    @Transactional
    public void getInLocation() throws Exception {
        // Initialize the database
        inLocationRepository.saveAndFlush(inLocation);

        // Get the inLocation
        restInLocationMockMvc.perform(get("/api/in-locations/{id}", inLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inLocation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInLocation() throws Exception {
        // Get the inLocation
        restInLocationMockMvc.perform(get("/api/in-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInLocation() throws Exception {
        // Initialize the database
        inLocationRepository.saveAndFlush(inLocation);
        int databaseSizeBeforeUpdate = inLocationRepository.findAll().size();

        // Update the inLocation
        InLocation updatedInLocation = inLocationRepository.findOne(inLocation.getId());
        updatedInLocation
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .address(UPDATED_ADDRESS);
        InLocationDTO inLocationDTO = inLocationMapper.toDto(updatedInLocation);

        restInLocationMockMvc.perform(put("/api/in-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inLocationDTO)))
            .andExpect(status().isOk());

        // Validate the InLocation in the database
        List<InLocation> inLocationList = inLocationRepository.findAll();
        assertThat(inLocationList).hasSize(databaseSizeBeforeUpdate);
        InLocation testInLocation = inLocationList.get(inLocationList.size() - 1);
        assertThat(testInLocation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInLocation.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testInLocation.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingInLocation() throws Exception {
        int databaseSizeBeforeUpdate = inLocationRepository.findAll().size();

        // Create the InLocation
        InLocationDTO inLocationDTO = inLocationMapper.toDto(inLocation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInLocationMockMvc.perform(put("/api/in-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inLocationDTO)))
            .andExpect(status().isCreated());

        // Validate the InLocation in the database
        List<InLocation> inLocationList = inLocationRepository.findAll();
        assertThat(inLocationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInLocation() throws Exception {
        // Initialize the database
        inLocationRepository.saveAndFlush(inLocation);
        int databaseSizeBeforeDelete = inLocationRepository.findAll().size();

        // Get the inLocation
        restInLocationMockMvc.perform(delete("/api/in-locations/{id}", inLocation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InLocation> inLocationList = inLocationRepository.findAll();
        assertThat(inLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InLocation.class);
        InLocation inLocation1 = new InLocation();
        inLocation1.setId(1L);
        InLocation inLocation2 = new InLocation();
        inLocation2.setId(inLocation1.getId());
        assertThat(inLocation1).isEqualTo(inLocation2);
        inLocation2.setId(2L);
        assertThat(inLocation1).isNotEqualTo(inLocation2);
        inLocation1.setId(null);
        assertThat(inLocation1).isNotEqualTo(inLocation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InLocationDTO.class);
        InLocationDTO inLocationDTO1 = new InLocationDTO();
        inLocationDTO1.setId(1L);
        InLocationDTO inLocationDTO2 = new InLocationDTO();
        assertThat(inLocationDTO1).isNotEqualTo(inLocationDTO2);
        inLocationDTO2.setId(inLocationDTO1.getId());
        assertThat(inLocationDTO1).isEqualTo(inLocationDTO2);
        inLocationDTO2.setId(2L);
        assertThat(inLocationDTO1).isNotEqualTo(inLocationDTO2);
        inLocationDTO1.setId(null);
        assertThat(inLocationDTO1).isNotEqualTo(inLocationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(inLocationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(inLocationMapper.fromId(null)).isNull();
    }
}
