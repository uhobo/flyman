package com.geller.charts.web.rest;

import com.geller.charts.ChartsApp;

import com.geller.charts.domain.SurveyResult;
import com.geller.charts.repository.SurveyResultRepository;
import com.geller.charts.service.SurveyResultService;
import com.geller.charts.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


import static com.geller.charts.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SurveyResultResource REST controller.
 *
 * @see SurveyResultResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChartsApp.class)
public class SurveyResultResourceIntTest {

    private static final LocalDate DEFAULT_SUBMIT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SUBMIT_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SurveyResultRepository surveyResultRepository;

    @Mock
    private SurveyResultRepository surveyResultRepositoryMock;
    

    @Mock
    private SurveyResultService surveyResultServiceMock;

    @Autowired
    private SurveyResultService surveyResultService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restSurveyResultMockMvc;

    private SurveyResult surveyResult;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SurveyResultResource surveyResultResource = new SurveyResultResource(surveyResultService);
        this.restSurveyResultMockMvc = MockMvcBuilders.standaloneSetup(surveyResultResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SurveyResult createEntity() {
        SurveyResult surveyResult = new SurveyResult()
            .submitDate(DEFAULT_SUBMIT_DATE);
        return surveyResult;
    }

    @Before
    public void initTest() {
        surveyResultRepository.deleteAll();
        surveyResult = createEntity();
    }

    @Test
    public void createSurveyResult() throws Exception {
        int databaseSizeBeforeCreate = surveyResultRepository.findAll().size();

        // Create the SurveyResult
        restSurveyResultMockMvc.perform(post("/api/survey-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(surveyResult)))
            .andExpect(status().isCreated());

        // Validate the SurveyResult in the database
        List<SurveyResult> surveyResultList = surveyResultRepository.findAll();
        assertThat(surveyResultList).hasSize(databaseSizeBeforeCreate + 1);
        SurveyResult testSurveyResult = surveyResultList.get(surveyResultList.size() - 1);
        assertThat(testSurveyResult.getSubmitDate()).isEqualTo(DEFAULT_SUBMIT_DATE);
    }

    @Test
    public void createSurveyResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = surveyResultRepository.findAll().size();

        // Create the SurveyResult with an existing ID
        surveyResult.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restSurveyResultMockMvc.perform(post("/api/survey-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(surveyResult)))
            .andExpect(status().isBadRequest());

        // Validate the SurveyResult in the database
        List<SurveyResult> surveyResultList = surveyResultRepository.findAll();
        assertThat(surveyResultList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllSurveyResults() throws Exception {
        // Initialize the database
        surveyResultRepository.save(surveyResult);

        // Get all the surveyResultList
        restSurveyResultMockMvc.perform(get("/api/survey-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(surveyResult.getId())))
            .andExpect(jsonPath("$.[*].submitDate").value(hasItem(DEFAULT_SUBMIT_DATE.toString())));
    }
    
    public void getAllSurveyResultsWithEagerRelationshipsIsEnabled() throws Exception {
        SurveyResultResource surveyResultResource = new SurveyResultResource(surveyResultServiceMock);
        when(surveyResultServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restSurveyResultMockMvc = MockMvcBuilders.standaloneSetup(surveyResultResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restSurveyResultMockMvc.perform(get("/api/survey-results?eagerload=true"))
        .andExpect(status().isOk());

        verify(surveyResultServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllSurveyResultsWithEagerRelationshipsIsNotEnabled() throws Exception {
        SurveyResultResource surveyResultResource = new SurveyResultResource(surveyResultServiceMock);
            when(surveyResultServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restSurveyResultMockMvc = MockMvcBuilders.standaloneSetup(surveyResultResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restSurveyResultMockMvc.perform(get("/api/survey-results?eagerload=true"))
        .andExpect(status().isOk());

            verify(surveyResultServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    public void getSurveyResult() throws Exception {
        // Initialize the database
        surveyResultRepository.save(surveyResult);

        // Get the surveyResult
        restSurveyResultMockMvc.perform(get("/api/survey-results/{id}", surveyResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(surveyResult.getId()))
            .andExpect(jsonPath("$.submitDate").value(DEFAULT_SUBMIT_DATE.toString()));
    }

    @Test
    public void getNonExistingSurveyResult() throws Exception {
        // Get the surveyResult
        restSurveyResultMockMvc.perform(get("/api/survey-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSurveyResult() throws Exception {
        // Initialize the database
        surveyResultService.save(surveyResult);

        int databaseSizeBeforeUpdate = surveyResultRepository.findAll().size();

        // Update the surveyResult
        SurveyResult updatedSurveyResult = surveyResultRepository.findById(surveyResult.getId()).get();
        updatedSurveyResult
            .submitDate(UPDATED_SUBMIT_DATE);

        restSurveyResultMockMvc.perform(put("/api/survey-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSurveyResult)))
            .andExpect(status().isOk());

        // Validate the SurveyResult in the database
        List<SurveyResult> surveyResultList = surveyResultRepository.findAll();
        assertThat(surveyResultList).hasSize(databaseSizeBeforeUpdate);
        SurveyResult testSurveyResult = surveyResultList.get(surveyResultList.size() - 1);
        assertThat(testSurveyResult.getSubmitDate()).isEqualTo(UPDATED_SUBMIT_DATE);
    }

    @Test
    public void updateNonExistingSurveyResult() throws Exception {
        int databaseSizeBeforeUpdate = surveyResultRepository.findAll().size();

        // Create the SurveyResult

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSurveyResultMockMvc.perform(put("/api/survey-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(surveyResult)))
            .andExpect(status().isBadRequest());

        // Validate the SurveyResult in the database
        List<SurveyResult> surveyResultList = surveyResultRepository.findAll();
        assertThat(surveyResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteSurveyResult() throws Exception {
        // Initialize the database
        surveyResultService.save(surveyResult);

        int databaseSizeBeforeDelete = surveyResultRepository.findAll().size();

        // Get the surveyResult
        restSurveyResultMockMvc.perform(delete("/api/survey-results/{id}", surveyResult.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SurveyResult> surveyResultList = surveyResultRepository.findAll();
        assertThat(surveyResultList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SurveyResult.class);
        SurveyResult surveyResult1 = new SurveyResult();
        surveyResult1.setId("id1");
        SurveyResult surveyResult2 = new SurveyResult();
        surveyResult2.setId(surveyResult1.getId());
        assertThat(surveyResult1).isEqualTo(surveyResult2);
        surveyResult2.setId("id2");
        assertThat(surveyResult1).isNotEqualTo(surveyResult2);
        surveyResult1.setId(null);
        assertThat(surveyResult1).isNotEqualTo(surveyResult2);
    }
}
