package com.eeu.smaartu.web.rest;

import com.eeu.smaartu.SmaartuApp;

import com.eeu.smaartu.domain.EndNode;
import com.eeu.smaartu.repository.EndNodeRepository;
import com.eeu.smaartu.service.EndNodeService;
import com.eeu.smaartu.service.SerialConnectionService;
import com.eeu.smaartu.service.dto.EndNodeDTO;
import com.eeu.smaartu.service.mapper.EndNodeMapper;
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

import com.eeu.smaartu.domain.enumeration.EndNodeType;
/**
 * Test class for the EndNodeResource REST controller.
 *
 * @see EndNodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmaartuApp.class)
public class EndNodeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final EndNodeType DEFAULT_TYPE = EndNodeType.GATEWAY;
    private static final EndNodeType UPDATED_TYPE = EndNodeType.REMOTE;

    @Autowired
    private EndNodeRepository endNodeRepository;

    @Autowired
    private EndNodeMapper endNodeMapper;

    @Autowired
    private EndNodeService endNodeService;

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

    private MockMvc restEndNodeMockMvc;

    private EndNode endNode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EndNodeResource endNodeResource = new EndNodeResource(endNodeService,serialConnectionService);
        this.restEndNodeMockMvc = MockMvcBuilders.standaloneSetup(endNodeResource)
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
    public static EndNode createEntity(EntityManager em) {
        EndNode endNode = new EndNode()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .type(DEFAULT_TYPE);
        return endNode;
    }

    @Before
    public void initTest() {
        endNode = createEntity(em);
    }

    @Test
    @Transactional
    public void createEndNode() throws Exception {
        int databaseSizeBeforeCreate = endNodeRepository.findAll().size();

        // Create the EndNode
        EndNodeDTO endNodeDTO = endNodeMapper.toDto(endNode);
        restEndNodeMockMvc.perform(post("/api/end-nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(endNodeDTO)))
            .andExpect(status().isCreated());

        // Validate the EndNode in the database
        List<EndNode> endNodeList = endNodeRepository.findAll();
        assertThat(endNodeList).hasSize(databaseSizeBeforeCreate + 1);
        EndNode testEndNode = endNodeList.get(endNodeList.size() - 1);
        assertThat(testEndNode.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEndNode.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testEndNode.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createEndNodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = endNodeRepository.findAll().size();

        // Create the EndNode with an existing ID
        endNode.setId(1L);
        EndNodeDTO endNodeDTO = endNodeMapper.toDto(endNode);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEndNodeMockMvc.perform(post("/api/end-nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(endNodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EndNode> endNodeList = endNodeRepository.findAll();
        assertThat(endNodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEndNodes() throws Exception {
        // Initialize the database
        endNodeRepository.saveAndFlush(endNode);

        // Get all the endNodeList
        restEndNodeMockMvc.perform(get("/api/end-nodes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(endNode.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getEndNode() throws Exception {
        // Initialize the database
        endNodeRepository.saveAndFlush(endNode);

        // Get the endNode
        restEndNodeMockMvc.perform(get("/api/end-nodes/{id}", endNode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(endNode.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEndNode() throws Exception {
        // Get the endNode
        restEndNodeMockMvc.perform(get("/api/end-nodes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEndNode() throws Exception {
        // Initialize the database
        endNodeRepository.saveAndFlush(endNode);
        int databaseSizeBeforeUpdate = endNodeRepository.findAll().size();

        // Update the endNode
        EndNode updatedEndNode = endNodeRepository.findOne(endNode.getId());
        updatedEndNode
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .type(UPDATED_TYPE);
        EndNodeDTO endNodeDTO = endNodeMapper.toDto(updatedEndNode);

        restEndNodeMockMvc.perform(put("/api/end-nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(endNodeDTO)))
            .andExpect(status().isOk());

        // Validate the EndNode in the database
        List<EndNode> endNodeList = endNodeRepository.findAll();
        assertThat(endNodeList).hasSize(databaseSizeBeforeUpdate);
        EndNode testEndNode = endNodeList.get(endNodeList.size() - 1);
        assertThat(testEndNode.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEndNode.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEndNode.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEndNode() throws Exception {
        int databaseSizeBeforeUpdate = endNodeRepository.findAll().size();

        // Create the EndNode
        EndNodeDTO endNodeDTO = endNodeMapper.toDto(endNode);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEndNodeMockMvc.perform(put("/api/end-nodes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(endNodeDTO)))
            .andExpect(status().isCreated());

        // Validate the EndNode in the database
        List<EndNode> endNodeList = endNodeRepository.findAll();
        assertThat(endNodeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEndNode() throws Exception {
        // Initialize the database
        endNodeRepository.saveAndFlush(endNode);
        int databaseSizeBeforeDelete = endNodeRepository.findAll().size();

        // Get the endNode
        restEndNodeMockMvc.perform(delete("/api/end-nodes/{id}", endNode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EndNode> endNodeList = endNodeRepository.findAll();
        assertThat(endNodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EndNode.class);
        EndNode endNode1 = new EndNode();
        endNode1.setId(1L);
        EndNode endNode2 = new EndNode();
        endNode2.setId(endNode1.getId());
        assertThat(endNode1).isEqualTo(endNode2);
        endNode2.setId(2L);
        assertThat(endNode1).isNotEqualTo(endNode2);
        endNode1.setId(null);
        assertThat(endNode1).isNotEqualTo(endNode2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EndNodeDTO.class);
        EndNodeDTO endNodeDTO1 = new EndNodeDTO();
        endNodeDTO1.setId(1L);
        EndNodeDTO endNodeDTO2 = new EndNodeDTO();
        assertThat(endNodeDTO1).isNotEqualTo(endNodeDTO2);
        endNodeDTO2.setId(endNodeDTO1.getId());
        assertThat(endNodeDTO1).isEqualTo(endNodeDTO2);
        endNodeDTO2.setId(2L);
        assertThat(endNodeDTO1).isNotEqualTo(endNodeDTO2);
        endNodeDTO1.setId(null);
        assertThat(endNodeDTO1).isNotEqualTo(endNodeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(endNodeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(endNodeMapper.fromId(null)).isNull();
    }
}
