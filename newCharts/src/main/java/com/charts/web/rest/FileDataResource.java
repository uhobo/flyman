package com.charts.web.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.charts.domain.charts.ChartRequest;
import com.charts.domain.charts.ChartResponse;
import com.charts.domain.treemenu.TreeNode;
import com.charts.domain.FileData;
import com.charts.domain.ImportExcelData;
import com.charts.repository.FileDataRepository;
import com.charts.service.ChartService;
import com.charts.service.ExcelService;
import com.charts.web.rest.errors.BadRequestAlertException;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.charts.domain.FileData}.
 */
@RestController
@RequestMapping("/api")
public class FileDataResource {

    private final Logger log = LoggerFactory.getLogger(FileDataResource.class);

    private static final String ENTITY_NAME = "fileData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileDataRepository fileDataRepository;

    private final ExcelService excelService;
    
    private final ChartService chartService;
    
    public FileDataResource(FileDataRepository fileDataRepository, ExcelService excelService, ChartService chartService) {
        this.fileDataRepository = fileDataRepository;
        this.excelService = excelService;
        this.chartService = chartService;
    }

    /**
     * {@code POST  /file-data} : Create a new fileData.
     *
     * @param fileData the fileData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileData, or with status {@code 400 (Bad Request)} if the fileData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/file-data")
    public ResponseEntity<FileData> createFileData(@Valid @RequestBody FileData fileData) throws URISyntaxException {
        log.debug("REST request to save FileData : {}", fileData);
        if (fileData.getId() != null) {
            throw new BadRequestAlertException("A new fileData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileData result = fileDataRepository.save(fileData);
        return ResponseEntity.created(new URI("/api/file-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /file-data} : Updates an existing fileData.
     *
     * @param fileData the fileData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileData,
     * or with status {@code 400 (Bad Request)} if the fileData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fileData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/file-data")
    public ResponseEntity<FileData> updateFileData(@Valid @RequestBody FileData fileData) throws URISyntaxException {
        log.debug("REST request to update FileData : {}", fileData);
        if (fileData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FileData result = fileDataRepository.save(fileData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileData.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /file-data} : get all the fileData.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fileData in body.
     */
    @GetMapping("/file-data")
    public ResponseEntity<List<FileData>> getAllFileData(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of FileData");
        Page<FileData> page = fileDataRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /file-data/:id} : get the "id" fileData.
     *
     * @param id the id of the fileData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/file-data/{id}")
    public ResponseEntity<FileData> getFileData(@PathVariable String id) {
        log.debug("REST request to get FileData : {}", id);
        Optional<FileData> fileData = fileDataRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fileData);
    }

    /**
     * {@code DELETE  /file-data/:id} : delete the "id" fileData.
     *
     * @param id the id of the fileData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/file-data/{id}")
    public ResponseEntity<Void> deleteFileData(@PathVariable String id) {
        log.debug("REST request to delete FileData : {}", id);
        fileDataRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
    
    @GetMapping("/file-data/createTreeMenu/{id}/{subjectType}/{menuByGroup}")
    public ResponseEntity<List<TreeNode>> createTreeMenu(@PathVariable String id, @PathVariable Integer subjectType, @PathVariable Boolean menuByGroup){
    	log.debug("REST request to createTreeMenu FileData : {}, Subject: {}", id, subjectType);
    	Optional<List<TreeNode>> treeNode = null;
		try {
			treeNode = this.chartService.createTreeTable(id, subjectType, menuByGroup);
		} catch (JsonProcessingException e) {
			log.error("Faild to handle request", e);
			e.printStackTrace();
		}
    	return ResponseUtil.wrapOrNotFound(treeNode);
    }
    
    
    @PostMapping("/file-data/upload")
    public ResponseEntity<FileData> Upload(@RequestBody MultipartFile file) throws IOException {
    	FileData fileData = new FileData();
    	InputStream in = file.getInputStream();
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + file.getOriginalFilename();
        int ch = 0;
        FileOutputStream f = new FileOutputStream(fileLocation);
		while ((ch = in.read()) != -1) {
            f.write(ch);
        }
        f.flush();
        f.close();
        fileData.setFileName(file.getOriginalFilename());
        LocalDate timestamp = LocalDate.now(ZoneId.systemDefault());;
		fileData.setTimestamp(timestamp );
        List<ImportExcelData> importExcelDatasList = this.excelService.parseExcelData(fileLocation);
        if(!importExcelDatasList.isEmpty()) {
        	fileData.setImportData(importExcelDatasList.get(0));
        }
        
        FileData result = fileDataRepository.save(fileData);
        //storageService.store(file);
    	return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, "uploaded"))
                .body(result);
    }
    
    @PostMapping("/file-data/chartData")
    public ResponseEntity<ChartResponse> getChartPackage(@RequestBody ChartRequest chartRequest){
    	
    	ChartResponse chartResponse  = null;
    	
    	
    	try {
			chartResponse  = chartService.getChartPackage(chartRequest);
		} catch (Exception e) {
			log.error("Failed getChartPackage", e);
		}
//    	try {
//    		 	
//    		
//    		
//    		if(chartResponse.getChartTypeStr().equals("bar")) {
//    			this.excelService.exportBarChart2Excel(chartResponse.getData(), false);	
//    		}else if(chartResponse.getChartTypeStr().equals("pie")) {
//    			this.excelService.exportPieChart2Excel(chartResponse.getData());
//    		}
//			
//		} catch (IOException e) {
//			log.error("faild to export to excel", e);
//		}
    	return ResponseUtil.wrapOrNotFound(Optional.of(chartResponse)); 
    }
    
    
   
   // @PostMapping(value ="/file-data/export2Excel", produces= MediaType.APPLICATION_OCTET_STREAM_VALUE)
    //public @ResponseBody byte[] export2Excel(@RequestBody ChartRequest chartRequest){
    //@PostMapping(value="/file-data/export2Excel")
    
    @PostMapping(value ="/file-data/export2Excel", produces= MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[]  export2Excel(@RequestBody ChartRequest chartRequest){
    		//ExportExcelFile exportExcelFile = null;
    	try {
    		
    		
    		
    		ChartResponse chartResponse = null;;
			try {
				chartResponse = chartService.getChartPackage(chartRequest);
			} catch (Exception e) {
				log.error("Failded getChartPackage", e);
			}
    		
    		String fileName = null;
    		if(chartResponse.getChartTypeStr().equals("bar")) {
    			fileName = this.excelService.exportBarChart2Excel(chartResponse.getData(), false);	
    		}else if(chartResponse.getChartTypeStr().equals("pie")) {
    			fileName = this.excelService.exportPieChart2Excel(chartResponse.getData());
    		}
    		
    		
    		
    		//exportExcelFile = new ExportExcelFile();
    		Path path = Paths.get(fileName);
    		return Files.readAllBytes(path);
    		//exportExcelFile.setFileName(path.toFile().getName());
    		//exportExcelFile.setByteArr(Files.readAllBytes(path));
    		//ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
    		//return ResponseUtil.wrapOrNotFound(Optional.of(exportExcelFile));
 //   		HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-Disposition", "attachment; filename=" + fileName);
            
//    		 return ResponseEntity.ok()
//    		            .headers(headers)
//    		            .contentLength(resource.getFile().length())
//    		            .contentType(MediaType.parseMediaType("application/octet-stream"))
//    		            .body(resource);
		} catch (IOException e) {
			log.error("faild to export to excel", e);
		}
    	return null;
    }
    

    
    
}
