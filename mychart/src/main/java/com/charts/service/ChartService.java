package com.charts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.charts.domain.FileData;
import com.charts.domain.charts.ChartRequest;
import com.charts.domain.charts.ChartResponse;
import com.charts.domain.charts.SubjectType;
import com.charts.domain.treemenu.TreeNode;
import com.charts.repository.FileDataRepository;
import com.charts.service.diagramhandlers.DiagramHandler;
import com.charts.service.handlers.meun.MenuHandler;
import com.fasterxml.jackson.core.JsonProcessingException;


@Service
public class ChartService {
	
	private final FileDataRepository fileDataRepository;
	//private final FileDataService fileDataService;
	
	@Autowired
	private ApplicationContext ctx;
	
	 
	public ChartService(FileDataRepository fileDataRepository) {
		this.fileDataRepository = fileDataRepository;
		//this.fileDataService = fileDataService;
	}
	
	public Optional<List<TreeNode>> createTreeTable(String id, Integer subjectType, boolean menuByGroup) throws JsonProcessingException {
		List<TreeNode> treeNode = null;
		Optional<FileData> fileData = this.fileDataRepository.findById(id);
		if(!fileData.isPresent()) {
			return Optional.ofNullable(treeNode);
		}
		SubjectType subject = SubjectType.getSubjectType(subjectType);
		if(subject == null) {
			return Optional.ofNullable(treeNode);
		}
		SubjectType subjectTypeEnum = SubjectType.getSubjectType(subjectType);
		
		MenuHandler disgramHandler = (MenuHandler)this.ctx.getBean(subjectTypeEnum.getMenuHandlerBeanName());
		return Optional.ofNullable(menuByGroup ? disgramHandler.createGroupTreeMenu(fileData.get()) : disgramHandler.createTreeMenu(fileData.get()));
	}
	    
	
	public ChartResponse getChartPackage(ChartRequest chartRequest) throws Exception {
		ChartResponse chartResponse = new ChartResponse();
		
		Optional<FileData> fileData = fileDataRepository.findById(chartRequest.getFileDataId());
		SubjectType subjectType = SubjectType.getSubjectType(chartRequest.getSelectedSubject());
		DiagramHandler disgramHandler = (DiagramHandler)this.ctx.getBean(subjectType.getDiagramHandlerBeanName());
		disgramHandler.createPackageResult(fileData, chartRequest, chartResponse);
		return chartResponse;
	}


	
	public ApplicationContext getCtx() {
		return ctx;
	}

	public void setCtx(ApplicationContext ctx) {
		this.ctx = ctx;
	}

	
}
