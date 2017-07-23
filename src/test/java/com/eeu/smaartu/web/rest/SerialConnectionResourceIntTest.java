package com.eeu.smaartu.web.rest;

import com.eeu.smaartu.SmaartuApp;

import com.eeu.smaartu.domain.SerialConnection;
import com.eeu.smaartu.repository.SerialConnectionRepository;
import com.eeu.smaartu.service.SerialConnectionService;
import com.eeu.smaartu.service.dto.SerialConnectionDTO;
import com.eeu.smaartu.service.mapper.SerialConnectionMapper;
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

import com.eeu.smaartu.domain.enumeration.DataBits;
import com.eeu.smaartu.domain.enumeration.Parity;
import com.eeu.smaartu.domain.enumeration.StopBits;
import com.eeu.smaartu.domain.enumeration.FlowControl;
/**
 * Test class for the SerialConnectionResource REST controller.
 *
 * @see SerialConnectionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmaartuApp.class)
public class SerialConnectionResourceIntTest {

    private static final String DEFAULT_PORT = "AAAAAAAAAA";
    private static final String UPDATED_PORT = "BBBBBBBBBB";

    private static final Long DEFAULT_BAUD_RATE = 1L;
    private static final Long UPDATED_BAUD_RATE = 2L;

    private static final DataBits DEFAULT_DATA_BITS = DataBits.B7;
    private static final DataBits UPDATED_DATA_BITS = DataBits.B8;

    private static final Parity DEFAULT_PARITY = Parity.NONE;
    private static final Parity UPDATED_PARITY = Parity.EVEN;

    private static final StopBits DEFAULT_STOP_BITS = StopBits.B1;
    private static final StopBits UPDATED_STOP_BITS = StopBits.B2;

    private static final FlowControl DEFAULT_FLOW_CONTROL = FlowControl.NONE;
    private static final FlowControl UPDATED_FLOW_CONTROL = FlowControl.HARDWARE;

    @Autowired
    private SerialConnectionRepository serialConnectionRepository;

    @Autowired
    private SerialConnectionMapper serialConnectionMapper;

    @Autowired
    private SerialConnectionService serialConnectionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSerialConnectionMockMvc;

    private SerialConnection serialConnection;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SerialConnectionResource serialConnectionResource = new SerialConnectionResource(serialConnectionService);
        this.restSerialConnectionMockMvc = MockMvcBuilders.standaloneSetup(serialConnectionResource)
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
    public static SerialConnection createEntity(EntityManager em) {
        SerialConnection serialConnection = new SerialConnection()
            .port(DEFAULT_PORT)
            .baudRate(DEFAULT_BAUD_RATE)
            .dataBits(DEFAULT_DATA_BITS)
            .parity(DEFAULT_PARITY)
            .stopBits(DEFAULT_STOP_BITS)
            .flowControl(DEFAULT_FLOW_CONTROL);
        return serialConnection;
    }

    @Before
    public void initTest() {
        serialConnection = createEntity(em);
    }

    @Test
    @Transactional
    public void createSerialConnection() throws Exception {
        int databaseSizeBeforeCreate = serialConnectionRepository.findAll().size();

        // Create the SerialConnection
        SerialConnectionDTO serialConnectionDTO = serialConnectionMapper.toDto(serialConnection);
        restSerialConnectionMockMvc.perform(post("/api/serial-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serialConnectionDTO)))
            .andExpect(status().isCreated());

        // Validate the SerialConnection in the database
        List<SerialConnection> serialConnectionList = serialConnectionRepository.findAll();
        assertThat(serialConnectionList).hasSize(databaseSizeBeforeCreate + 1);
        SerialConnection testSerialConnection = serialConnectionList.get(serialConnectionList.size() - 1);
        assertThat(testSerialConnection.getPort()).isEqualTo(DEFAULT_PORT);
        assertThat(testSerialConnection.getBaudRate()).isEqualTo(DEFAULT_BAUD_RATE);
        assertThat(testSerialConnection.getDataBits()).isEqualTo(DEFAULT_DATA_BITS);
        assertThat(testSerialConnection.getParity()).isEqualTo(DEFAULT_PARITY);
        assertThat(testSerialConnection.getStopBits()).isEqualTo(DEFAULT_STOP_BITS);
        assertThat(testSerialConnection.getFlowControl()).isEqualTo(DEFAULT_FLOW_CONTROL);
    }

    @Test
    @Transactional
    public void createSerialConnectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serialConnectionRepository.findAll().size();

        // Create the SerialConnection with an existing ID
        serialConnection.setId(1L);
        SerialConnectionDTO serialConnectionDTO = serialConnectionMapper.toDto(serialConnection);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSerialConnectionMockMvc.perform(post("/api/serial-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serialConnectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SerialConnection> serialConnectionList = serialConnectionRepository.findAll();
        assertThat(serialConnectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSerialConnections() throws Exception {
        // Initialize the database
        serialConnectionRepository.saveAndFlush(serialConnection);

        // Get all the serialConnectionList
        restSerialConnectionMockMvc.perform(get("/api/serial-connections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serialConnection.getId().intValue())))
            .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT.toString())))
            .andExpect(jsonPath("$.[*].baudRate").value(hasItem(DEFAULT_BAUD_RATE.intValue())))
            .andExpect(jsonPath("$.[*].dataBits").value(hasItem(DEFAULT_DATA_BITS.toString())))
            .andExpect(jsonPath("$.[*].parity").value(hasItem(DEFAULT_PARITY.toString())))
            .andExpect(jsonPath("$.[*].stopBits").value(hasItem(DEFAULT_STOP_BITS.toString())))
            .andExpect(jsonPath("$.[*].flowControl").value(hasItem(DEFAULT_FLOW_CONTROL.toString())));
    }

    @Test
    @Transactional
    public void getSerialConnection() throws Exception {
        // Initialize the database
        serialConnectionRepository.saveAndFlush(serialConnection);

        // Get the serialConnection
        restSerialConnectionMockMvc.perform(get("/api/serial-connections/{id}", serialConnection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serialConnection.getId().intValue()))
            .andExpect(jsonPath("$.port").value(DEFAULT_PORT.toString()))
            .andExpect(jsonPath("$.baudRate").value(DEFAULT_BAUD_RATE.intValue()))
            .andExpect(jsonPath("$.dataBits").value(DEFAULT_DATA_BITS.toString()))
            .andExpect(jsonPath("$.parity").value(DEFAULT_PARITY.toString()))
            .andExpect(jsonPath("$.stopBits").value(DEFAULT_STOP_BITS.toString()))
            .andExpect(jsonPath("$.flowControl").value(DEFAULT_FLOW_CONTROL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSerialConnection() throws Exception {
        // Get the serialConnection
        restSerialConnectionMockMvc.perform(get("/api/serial-connections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSerialConnection() throws Exception {
        // Initialize the database
        serialConnectionRepository.saveAndFlush(serialConnection);
        int databaseSizeBeforeUpdate = serialConnectionRepository.findAll().size();

        // Update the serialConnection
        SerialConnection updatedSerialConnection = serialConnectionRepository.findOne(serialConnection.getId());
        updatedSerialConnection
            .port(UPDATED_PORT)
            .baudRate(UPDATED_BAUD_RATE)
            .dataBits(UPDATED_DATA_BITS)
            .parity(UPDATED_PARITY)
            .stopBits(UPDATED_STOP_BITS)
            .flowControl(UPDATED_FLOW_CONTROL);
        SerialConnectionDTO serialConnectionDTO = serialConnectionMapper.toDto(updatedSerialConnection);

        restSerialConnectionMockMvc.perform(put("/api/serial-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serialConnectionDTO)))
            .andExpect(status().isOk());

        // Validate the SerialConnection in the database
        List<SerialConnection> serialConnectionList = serialConnectionRepository.findAll();
        assertThat(serialConnectionList).hasSize(databaseSizeBeforeUpdate);
        SerialConnection testSerialConnection = serialConnectionList.get(serialConnectionList.size() - 1);
        assertThat(testSerialConnection.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testSerialConnection.getBaudRate()).isEqualTo(UPDATED_BAUD_RATE);
        assertThat(testSerialConnection.getDataBits()).isEqualTo(UPDATED_DATA_BITS);
        assertThat(testSerialConnection.getParity()).isEqualTo(UPDATED_PARITY);
        assertThat(testSerialConnection.getStopBits()).isEqualTo(UPDATED_STOP_BITS);
        assertThat(testSerialConnection.getFlowControl()).isEqualTo(UPDATED_FLOW_CONTROL);
    }

    @Test
    @Transactional
    public void updateNonExistingSerialConnection() throws Exception {
        int databaseSizeBeforeUpdate = serialConnectionRepository.findAll().size();

        // Create the SerialConnection
        SerialConnectionDTO serialConnectionDTO = serialConnectionMapper.toDto(serialConnection);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSerialConnectionMockMvc.perform(put("/api/serial-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serialConnectionDTO)))
            .andExpect(status().isCreated());

        // Validate the SerialConnection in the database
        List<SerialConnection> serialConnectionList = serialConnectionRepository.findAll();
        assertThat(serialConnectionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSerialConnection() throws Exception {
        // Initialize the database
        serialConnectionRepository.saveAndFlush(serialConnection);
        int databaseSizeBeforeDelete = serialConnectionRepository.findAll().size();

        // Get the serialConnection
        restSerialConnectionMockMvc.perform(delete("/api/serial-connections/{id}", serialConnection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SerialConnection> serialConnectionList = serialConnectionRepository.findAll();
        assertThat(serialConnectionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SerialConnection.class);
        SerialConnection serialConnection1 = new SerialConnection();
        serialConnection1.setId(1L);
        SerialConnection serialConnection2 = new SerialConnection();
        serialConnection2.setId(serialConnection1.getId());
        assertThat(serialConnection1).isEqualTo(serialConnection2);
        serialConnection2.setId(2L);
        assertThat(serialConnection1).isNotEqualTo(serialConnection2);
        serialConnection1.setId(null);
        assertThat(serialConnection1).isNotEqualTo(serialConnection2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SerialConnectionDTO.class);
        SerialConnectionDTO serialConnectionDTO1 = new SerialConnectionDTO();
        serialConnectionDTO1.setId(1L);
        SerialConnectionDTO serialConnectionDTO2 = new SerialConnectionDTO();
        assertThat(serialConnectionDTO1).isNotEqualTo(serialConnectionDTO2);
        serialConnectionDTO2.setId(serialConnectionDTO1.getId());
        assertThat(serialConnectionDTO1).isEqualTo(serialConnectionDTO2);
        serialConnectionDTO2.setId(2L);
        assertThat(serialConnectionDTO1).isNotEqualTo(serialConnectionDTO2);
        serialConnectionDTO1.setId(null);
        assertThat(serialConnectionDTO1).isNotEqualTo(serialConnectionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serialConnectionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serialConnectionMapper.fromId(null)).isNull();
    }
}
