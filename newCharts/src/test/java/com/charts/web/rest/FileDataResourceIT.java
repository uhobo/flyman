package com.charts.web.rest;

import com.charts.NewChartsApp;
import com.charts.domain.FileData;
import com.charts.repository.FileDataRepository;
import com.charts.service.ChartService;
import com.charts.service.ExcelService;
import com.charts.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.charts.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link FileDataResource} REST controller.
 */
@SpringBootTest(classes = NewChartsApp.class)
public class FileDataResourceIT {

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TIMESTAMP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TIMESTAMP = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private FileDataRepository fileDataRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;
    
    @Autowired
    private ExcelService excelService;
    
    @Autowired
    private ChartService chartService;
    
    private MockMvc restFileDataMockMvc;

    private FileData fileData;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FileDataResource fileDataResource = new FileDataResource(fileDataRepository, excelService, chartService);
        this.restFileDataMockMvc = MockMvcBuilders.standaloneSetup(fileDataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileData createEntity() {
        FileData fileData = new FileData()
            .fileName(DEFAULT_FILE_NAME)
            .title(DEFAULT_TITLE)
            .timestamp(DEFAULT_TIMESTAMP);
        return fileData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileData createUpdatedEntity() {
        FileData fileData = new FileData()
            .fileName(UPDATED_FILE_NAME)
            .title(UPDATED_TITLE)
            .timestamp(UPDATED_TIMESTAMP);
        return fileData;
    }

    @BeforeEach
    public void initTest() {
        fileDataRepository.deleteAll();
        fileData = createEntity();
    }

    @Test
    public void createFileData() throws Exception {
        int databaseSizeBeforeCreate = fileDataRepository.findAll().size();

        // Create the FileData
        restFileDataMockMvc.perform(post("/api/file-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileData)))
            .andExpect(status().isCreated());

        // Validate the FileData in the database
        List<FileData> fileDataList = fileDataRepository.findAll();
        assertThat(fileDataList).hasSize(databaseSizeBeforeCreate + 1);
        FileData testFileData = fileDataList.get(fileDataList.size() - 1);
        assertThat(testFileData.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testFileData.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testFileData.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    public void createFileDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileDataRepository.findAll().size();

        // Create the FileData with an existing ID
        fileData.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileDataMockMvc.perform(post("/api/file-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileData)))
            .andExpect(status().isBadRequest());

        // Validate the FileData in the database
        List<FileData> fileDataList = fileDataRepository.findAll();
        assertThat(fileDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileDataRepository.findAll().size();
        // set the field null
        fileData.setFileName(null);

        // Create the FileData, which fails.

        restFileDataMockMvc.perform(post("/api/file-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileData)))
            .andExpect(status().isBadRequest());

        List<FileData> fileDataList = fileDataRepository.findAll();
        assertThat(fileDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileDataRepository.findAll().size();
        // set the field null
        fileData.setTitle(null);

        // Create the FileData, which fails.

        restFileDataMockMvc.perform(post("/api/file-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileData)))
            .andExpect(status().isBadRequest());

        List<FileData> fileDataList = fileDataRepository.findAll();
        assertThat(fileDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileDataRepository.findAll().size();
        // set the field null
        fileData.setTimestamp(null);

        // Create the FileData, which fails.

        restFileDataMockMvc.perform(post("/api/file-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileData)))
            .andExpect(status().isBadRequest());

        List<FileData> fileDataList = fileDataRepository.findAll();
        assertThat(fileDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllFileData() throws Exception {
        // Initialize the database
        fileDataRepository.save(fileData);

        // Get all the fileDataList
        restFileDataMockMvc.perform(get("/api/file-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileData.getId())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())));
    }
    
    @Test
    public void getFileData() throws Exception {
        // Initialize the database
        fileDataRepository.save(fileData);

        // Get the fileData
        restFileDataMockMvc.perform(get("/api/file-data/{id}", fileData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fileData.getId()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()));
    }

    @Test
    public void getNonExistingFileData() throws Exception {
        // Get the fileData
        restFileDataMockMvc.perform(get("/api/file-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFileData() throws Exception {
        // Initialize the database
        fileDataRepository.save(fileData);

        int databaseSizeBeforeUpdate = fileDataRepository.findAll().size();

        // Update the fileData
        FileData updatedFileData = fileDataRepository.findById(fileData.getId()).get();
        updatedFileData
            .fileName(UPDATED_FILE_NAME)
            .title(UPDATED_TITLE)
            .timestamp(UPDATED_TIMESTAMP);

        restFileDataMockMvc.perform(put("/api/file-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFileData)))
            .andExpect(status().isOk());

        // Validate the FileData in the database
        List<FileData> fileDataList = fileDataRepository.findAll();
        assertThat(fileDataList).hasSize(databaseSizeBeforeUpdate);
        FileData testFileData = fileDataList.get(fileDataList.size() - 1);
        assertThat(testFileData.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testFileData.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testFileData.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    public void updateNonExistingFileData() throws Exception {
        int databaseSizeBeforeUpdate = fileDataRepository.findAll().size();

        // Create the FileData

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileDataMockMvc.perform(put("/api/file-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileData)))
            .andExpect(status().isBadRequest());

        // Validate the FileData in the database
        List<FileData> fileDataList = fileDataRepository.findAll();
        assertThat(fileDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteFileData() throws Exception {
        // Initialize the database
        fileDataRepository.save(fileData);

        int databaseSizeBeforeDelete = fileDataRepository.findAll().size();

        // Delete the fileData
        restFileDataMockMvc.perform(delete("/api/file-data/{id}", fileData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FileData> fileDataList = fileDataRepository.findAll();
        assertThat(fileDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileData.class);
        FileData fileData1 = new FileData();
        fileData1.setId("id1");
        FileData fileData2 = new FileData();
        fileData2.setId(fileData1.getId());
        assertThat(fileData1).isEqualTo(fileData2);
        fileData2.setId("id2");
        assertThat(fileData1).isNotEqualTo(fileData2);
        fileData1.setId(null);
        assertThat(fileData1).isNotEqualTo(fileData2);
    }
}
