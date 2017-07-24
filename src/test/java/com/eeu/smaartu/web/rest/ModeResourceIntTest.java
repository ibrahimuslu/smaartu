package com.eeu.smaartu.web.rest;

import com.eeu.smaartu.SmaartuApp;

import com.eeu.smaartu.domain.Mode;
import com.eeu.smaartu.repository.ModeRepository;
import com.eeu.smaartu.service.ModeService;
import com.eeu.smaartu.service.dto.ModeDTO;
import com.eeu.smaartu.service.mapper.ModeMapper;
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

import com.eeu.smaartu.domain.enumeration.UnitStatus;
/**
 * Test class for the ModeResource REST controller.
 *
 * @see ModeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmaartuApp.class)
public class ModeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final UnitStatus DEFAULT_STATUS = UnitStatus.START;
    private static final UnitStatus UPDATED_STATUS = UnitStatus.STOP;

    @Autowired
    private ModeRepository modeRepository;

    @Autowired
    private ModeMapper modeMapper;

    @Autowired
    private ModeService modeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restModeMockMvc;

    private Mode mode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ModeResource modeResource = new ModeResource(modeService);
        this.restModeMockMvc = MockMvcBuilders.standaloneSetup(modeResource)
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
    public static Mode createEntity(EntityManager em) {
        Mode mode = new Mode()
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS);
        return mode;
    }

    @Before
    public void initTest() {
        mode = createEntity(em);
    }

    @Test
    @Transactional
    public void createMode() throws Exception {
        int databaseSizeBeforeCreate = modeRepository.findAll().size();

        // Create the Mode
        ModeDTO modeDTO = modeMapper.toDto(mode);
        restModeMockMvc.perform(post("/api/modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeDTO)))
            .andExpect(status().isCreated());

        // Validate the Mode in the database
        List<Mode> modeList = modeRepository.findAll();
        assertThat(modeList).hasSize(databaseSizeBeforeCreate + 1);
        Mode testMode = modeList.get(modeList.size() - 1);
        assertThat(testMode.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMode.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createModeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modeRepository.findAll().size();

        // Create the Mode with an existing ID
        mode.setId(1L);
        ModeDTO modeDTO = modeMapper.toDto(mode);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModeMockMvc.perform(post("/api/modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Mode> modeList = modeRepository.findAll();
        assertThat(modeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllModes() throws Exception {
        // Initialize the database
        modeRepository.saveAndFlush(mode);

        // Get all the modeList
        restModeMockMvc.perform(get("/api/modes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mode.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getMode() throws Exception {
        // Initialize the database
        modeRepository.saveAndFlush(mode);

        // Get the mode
        restModeMockMvc.perform(get("/api/modes/{id}", mode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mode.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMode() throws Exception {
        // Get the mode
        restModeMockMvc.perform(get("/api/modes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMode() throws Exception {
        // Initialize the database
        modeRepository.saveAndFlush(mode);
        int databaseSizeBeforeUpdate = modeRepository.findAll().size();

        // Update the mode
        Mode updatedMode = modeRepository.findOne(mode.getId());
        updatedMode
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS);
        ModeDTO modeDTO = modeMapper.toDto(updatedMode);

        restModeMockMvc.perform(put("/api/modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeDTO)))
            .andExpect(status().isOk());

        // Validate the Mode in the database
        List<Mode> modeList = modeRepository.findAll();
        assertThat(modeList).hasSize(databaseSizeBeforeUpdate);
        Mode testMode = modeList.get(modeList.size() - 1);
        assertThat(testMode.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMode.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingMode() throws Exception {
        int databaseSizeBeforeUpdate = modeRepository.findAll().size();

        // Create the Mode
        ModeDTO modeDTO = modeMapper.toDto(mode);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restModeMockMvc.perform(put("/api/modes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeDTO)))
            .andExpect(status().isCreated());

        // Validate the Mode in the database
        List<Mode> modeList = modeRepository.findAll();
        assertThat(modeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMode() throws Exception {
        // Initialize the database
        modeRepository.saveAndFlush(mode);
        int databaseSizeBeforeDelete = modeRepository.findAll().size();

        // Get the mode
        restModeMockMvc.perform(delete("/api/modes/{id}", mode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mode> modeList = modeRepository.findAll();
        assertThat(modeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mode.class);
        Mode mode1 = new Mode();
        mode1.setId(1L);
        Mode mode2 = new Mode();
        mode2.setId(mode1.getId());
        assertThat(mode1).isEqualTo(mode2);
        mode2.setId(2L);
        assertThat(mode1).isNotEqualTo(mode2);
        mode1.setId(null);
        assertThat(mode1).isNotEqualTo(mode2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModeDTO.class);
        ModeDTO modeDTO1 = new ModeDTO();
        modeDTO1.setId(1L);
        ModeDTO modeDTO2 = new ModeDTO();
        assertThat(modeDTO1).isNotEqualTo(modeDTO2);
        modeDTO2.setId(modeDTO1.getId());
        assertThat(modeDTO1).isEqualTo(modeDTO2);
        modeDTO2.setId(2L);
        assertThat(modeDTO1).isNotEqualTo(modeDTO2);
        modeDTO1.setId(null);
        assertThat(modeDTO1).isNotEqualTo(modeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(modeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(modeMapper.fromId(null)).isNull();
    }
}
