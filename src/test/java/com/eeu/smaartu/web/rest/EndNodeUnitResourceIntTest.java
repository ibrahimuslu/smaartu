package com.eeu.smaartu.web.rest;

import com.eeu.smaartu.SmaartuApp;

import com.eeu.smaartu.domain.EndNodeUnit;
import com.eeu.smaartu.repository.EndNodeUnitRepository;
import com.eeu.smaartu.service.EndNodeUnitService;
import com.eeu.smaartu.service.dto.EndNodeUnitDTO;
import com.eeu.smaartu.service.mapper.EndNodeUnitMapper;
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

import com.eeu.smaartu.domain.enumeration.UnitType;
import com.eeu.smaartu.domain.enumeration.UnitStatus;
/**
 * Test class for the EndNodeUnitResource REST controller.
 *
 * @see EndNodeUnitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmaartuApp.class)
public class EndNodeUnitResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final UnitType DEFAULT_TYPE = UnitType.SENSOR;
    private static final UnitType UPDATED_TYPE = UnitType.CONTROL;

    private static final UnitStatus DEFAULT_STATUS = UnitStatus.START;
    private static final UnitStatus UPDATED_STATUS = UnitStatus.STOP;

    @Autowired
    private EndNodeUnitRepository endNodeUnitRepository;

    @Autowired
    private EndNodeUnitMapper endNodeUnitMapper;

    @Autowired
    private EndNodeUnitService endNodeUnitService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEndNodeUnitMockMvc;

    private EndNodeUnit endNodeUnit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EndNodeUnitResource endNodeUnitResource = new EndNodeUnitResource(endNodeUnitService);
        this.restEndNodeUnitMockMvc = MockMvcBuilders.standaloneSetup(endNodeUnitResource)
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
    public static EndNodeUnit createEntity(EntityManager em) {
        EndNodeUnit endNodeUnit = new EndNodeUnit()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS);
        return endNodeUnit;
    }

    @Before
    public void initTest() {
        endNodeUnit = createEntity(em);
    }

    @Test
    @Transactional
    public void createEndNodeUnit() throws Exception {
        int databaseSizeBeforeCreate = endNodeUnitRepository.findAll().size();

        // Create the EndNodeUnit
        EndNodeUnitDTO endNodeUnitDTO = endNodeUnitMapper.toDto(endNodeUnit);
        restEndNodeUnitMockMvc.perform(post("/api/end-node-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(endNodeUnitDTO)))
            .andExpect(status().isCreated());

        // Validate the EndNodeUnit in the database
        List<EndNodeUnit> endNodeUnitList = endNodeUnitRepository.findAll();
        assertThat(endNodeUnitList).hasSize(databaseSizeBeforeCreate + 1);
        EndNodeUnit testEndNodeUnit = endNodeUnitList.get(endNodeUnitList.size() - 1);
        assertThat(testEndNodeUnit.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEndNodeUnit.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEndNodeUnit.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createEndNodeUnitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = endNodeUnitRepository.findAll().size();

        // Create the EndNodeUnit with an existing ID
        endNodeUnit.setId(1L);
        EndNodeUnitDTO endNodeUnitDTO = endNodeUnitMapper.toDto(endNodeUnit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEndNodeUnitMockMvc.perform(post("/api/end-node-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(endNodeUnitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EndNodeUnit> endNodeUnitList = endNodeUnitRepository.findAll();
        assertThat(endNodeUnitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEndNodeUnits() throws Exception {
        // Initialize the database
        endNodeUnitRepository.saveAndFlush(endNodeUnit);

        // Get all the endNodeUnitList
        restEndNodeUnitMockMvc.perform(get("/api/end-node-units?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(endNodeUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getEndNodeUnit() throws Exception {
        // Initialize the database
        endNodeUnitRepository.saveAndFlush(endNodeUnit);

        // Get the endNodeUnit
        restEndNodeUnitMockMvc.perform(get("/api/end-node-units/{id}", endNodeUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(endNodeUnit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEndNodeUnit() throws Exception {
        // Get the endNodeUnit
        restEndNodeUnitMockMvc.perform(get("/api/end-node-units/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEndNodeUnit() throws Exception {
        // Initialize the database
        endNodeUnitRepository.saveAndFlush(endNodeUnit);
        int databaseSizeBeforeUpdate = endNodeUnitRepository.findAll().size();

        // Update the endNodeUnit
        EndNodeUnit updatedEndNodeUnit = endNodeUnitRepository.findOne(endNodeUnit.getId());
        updatedEndNodeUnit
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS);
        EndNodeUnitDTO endNodeUnitDTO = endNodeUnitMapper.toDto(updatedEndNodeUnit);

        restEndNodeUnitMockMvc.perform(put("/api/end-node-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(endNodeUnitDTO)))
            .andExpect(status().isOk());

        // Validate the EndNodeUnit in the database
        List<EndNodeUnit> endNodeUnitList = endNodeUnitRepository.findAll();
        assertThat(endNodeUnitList).hasSize(databaseSizeBeforeUpdate);
        EndNodeUnit testEndNodeUnit = endNodeUnitList.get(endNodeUnitList.size() - 1);
        assertThat(testEndNodeUnit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEndNodeUnit.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEndNodeUnit.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingEndNodeUnit() throws Exception {
        int databaseSizeBeforeUpdate = endNodeUnitRepository.findAll().size();

        // Create the EndNodeUnit
        EndNodeUnitDTO endNodeUnitDTO = endNodeUnitMapper.toDto(endNodeUnit);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEndNodeUnitMockMvc.perform(put("/api/end-node-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(endNodeUnitDTO)))
            .andExpect(status().isCreated());

        // Validate the EndNodeUnit in the database
        List<EndNodeUnit> endNodeUnitList = endNodeUnitRepository.findAll();
        assertThat(endNodeUnitList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEndNodeUnit() throws Exception {
        // Initialize the database
        endNodeUnitRepository.saveAndFlush(endNodeUnit);
        int databaseSizeBeforeDelete = endNodeUnitRepository.findAll().size();

        // Get the endNodeUnit
        restEndNodeUnitMockMvc.perform(delete("/api/end-node-units/{id}", endNodeUnit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EndNodeUnit> endNodeUnitList = endNodeUnitRepository.findAll();
        assertThat(endNodeUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EndNodeUnit.class);
        EndNodeUnit endNodeUnit1 = new EndNodeUnit();
        endNodeUnit1.setId(1L);
        EndNodeUnit endNodeUnit2 = new EndNodeUnit();
        endNodeUnit2.setId(endNodeUnit1.getId());
        assertThat(endNodeUnit1).isEqualTo(endNodeUnit2);
        endNodeUnit2.setId(2L);
        assertThat(endNodeUnit1).isNotEqualTo(endNodeUnit2);
        endNodeUnit1.setId(null);
        assertThat(endNodeUnit1).isNotEqualTo(endNodeUnit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EndNodeUnitDTO.class);
        EndNodeUnitDTO endNodeUnitDTO1 = new EndNodeUnitDTO();
        endNodeUnitDTO1.setId(1L);
        EndNodeUnitDTO endNodeUnitDTO2 = new EndNodeUnitDTO();
        assertThat(endNodeUnitDTO1).isNotEqualTo(endNodeUnitDTO2);
        endNodeUnitDTO2.setId(endNodeUnitDTO1.getId());
        assertThat(endNodeUnitDTO1).isEqualTo(endNodeUnitDTO2);
        endNodeUnitDTO2.setId(2L);
        assertThat(endNodeUnitDTO1).isNotEqualTo(endNodeUnitDTO2);
        endNodeUnitDTO1.setId(null);
        assertThat(endNodeUnitDTO1).isNotEqualTo(endNodeUnitDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(endNodeUnitMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(endNodeUnitMapper.fromId(null)).isNull();
    }
}
