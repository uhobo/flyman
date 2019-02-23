package com.geller.charts.service.impl;

import com.geller.charts.service.AskedService;
import com.geller.charts.service.RespondingService;
import com.geller.charts.service.SurveyResultService;
import com.geller.charts.service.SurveyService;
import com.geller.charts.domain.AnswerScale;
import com.geller.charts.domain.Asked;
import com.geller.charts.domain.AskedResult;
import com.geller.charts.domain.ColorPoolElm;
import com.geller.charts.domain.Question;
import com.geller.charts.domain.Responding;
import com.geller.charts.domain.RespondingSurveyInput;
import com.geller.charts.domain.Survey;
import com.geller.charts.domain.SurveyResult;
import com.geller.charts.domain.charts.BarChartDataSet;
import com.geller.charts.domain.charts.ChartCriteria;
import com.geller.charts.domain.charts.ChartData;
import com.geller.charts.domain.charts.ChartDataSetInteface;
import com.geller.charts.domain.charts.ChartDataWrapper;
import com.geller.charts.domain.charts.ChartTypeEnum;
import com.geller.charts.domain.charts.PieChartDataSet;
import com.geller.charts.domain.inputModel.SelectItem;
import com.geller.charts.domain.menus.MenuItem;
import com.geller.charts.domain.menus.MenuMetaData;
import com.geller.charts.repository.ColorsPoolRepository;
import com.geller.charts.repository.SurveyResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing SurveyResult.
 */
@Service
public class SurveyResultServiceImpl implements SurveyResultService {

    private final Logger log = LoggerFactory.getLogger(SurveyResultServiceImpl.class);

    private SurveyResultRepository surveyResultRepository;
    
    private  ColorsPoolRepository colorsPoolRepository;
    
    @Autowired
    private SurveyService surveyService;
    @Autowired
    private RespondingService respondingService;
    @Autowired
    private AskedService askedService;

    private static DecimalFormat decimalFormat = new DecimalFormat("#0.##");
    
    public SurveyResultServiceImpl(SurveyResultRepository surveyResultRepository, ColorsPoolRepository colorsPoolRepository) {
        this.surveyResultRepository = surveyResultRepository;
        this.colorsPoolRepository = colorsPoolRepository;
    }

    /**
     * Save a surveyResult.
     *
     * @param surveyResult the entity to save
     * @return the persisted entity
     */
    @Override
    public SurveyResult save(SurveyResult surveyResult) {
        log.debug("Request to save SurveyResult : {}", surveyResult);
        
        surveyResult.setRespondingId(surveyResult.getResponding().getId());
        surveyResult.setSurveyId(surveyResult.getSurvey().getId());
        surveyResult.getAskedResult().setAskedId(surveyResult.getAskedResult().getAsked().getId());
        return surveyResultRepository.save(surveyResult);
    }

    /**
     * Get all the surveyResults.
     *
     * @return the list of entities
     */
    @Override
    public List<SurveyResult> findAll() {
        log.debug("Request to get all SurveyResults");
        List<SurveyResult> resultList = surveyResultRepository.findAllWithEagerRelationships();
        initRefrences(resultList);
        return surveyResultRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the SurveyResult with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<SurveyResult> findAllWithEagerRelationships(Pageable pageable) {
        return surveyResultRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one surveyResult by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<SurveyResult> findOne(String id) {
        log.debug("Request to get SurveyResult : {}", id);
        return surveyResultRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the surveyResult by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete SurveyResult : {}", id);
        surveyResultRepository.deleteById(id);
    }
    
    public List<SurveyResult> findByRespondingId(String respondingId){
    	 List<SurveyResult> result = surveyResultRepository.findByResponding(respondingId);
    	 initRefrences(result);
    	 return result;
    }

	@Override
	public List<SurveyResult> findBySurveyAndResponding(String surveyId, String respondingId) {
		List<SurveyResult> resultList = surveyResultRepository.findBySurveyAndReponsing(surveyId, respondingId);
		initRefrences(resultList);
		return resultList;
	}
    
    private void initRefrences(List<SurveyResult> resultList) {
    	 for(SurveyResult surveyResult : resultList) {
         	surveyResult.setSurvey(this.surveyService.findOne(surveyResult.getSurveyId()).get());
         	surveyResult.setResponding(respondingService.findOne(surveyResult.getRespondingId()).get());
         	surveyResult.getAskedResult().setAsked(askedService.findOne(surveyResult.getAskedResult().getAskedId()).get());
         }
    }

	public SurveyService getSurveyService() {
		return surveyService;
	}

	public void setSurveyService(SurveyService surveyService) {
		this.surveyService = surveyService;
	}

	public RespondingService getRespondingService() {
		return respondingService;
	}

	public void setRespondingService(RespondingService respondingService) {
		this.respondingService = respondingService;
	}

	public AskedService getAskedService() {
		return askedService;
	}

	
	public void setAskedService(AskedService askedService) {
		this.askedService = askedService;
	}
    
	@Override
	public List<SurveyResult> publish(RespondingSurveyInput respondingSurveyInput) {
		//create responseResult documents
		List<Asked> askedList = this.askedService.findAll();
		List<SurveyResult> surveyResultList = new ArrayList<SurveyResult>();
		for(Asked asked :askedList) {
			SurveyResult surveyResult = new SurveyResult();
			surveyResult.setResponding(respondingSurveyInput.getResponding());
			surveyResult.setSurvey(respondingSurveyInput.getSurvey());
			AskedResult askedResult = new AskedResult();
			askedResult.setAsked(asked);
			surveyResult.setAskedResult(askedResult);
			this.save(surveyResult);
			surveyResultList.add(surveyResult);
		}
		
		return surveyResultList;
	}

	@Override
	public List<MenuItem> getSurveyMenu() {
		
		List<MenuItem> menuItems = new ArrayList<>();
		
		List<Survey> surveyList = this.surveyService.findAll();
		
		for(Survey survey: surveyList) {
			MenuMetaData menuMetaData = new MenuMetaData();
			
			MenuItem menuItem = new MenuItem();
			menuItem.setData(survey.getId());
			menuMetaData.setChartCriteria(new ChartCriteria());
			menuMetaData.getChartCriteria().setSurveyId(survey.getId());
			menuItem.setLabel(survey.getTitle());
			menuMetaData.setChartSelectList(new ArrayList<SelectItem>());
			
			for(Question question :survey.getQuestions()) {
				SelectItem item = new SelectItem();
				item.setLabel(question.getDescription());
				
				item.setValue(question.getQuestionId());
				menuMetaData.getChartSelectList().add(item);
			}
			menuItem.setData(menuMetaData);
			
			List<Responding> respondingList = this.respondingService.getRespondingBySurvey(survey.getId());
			for(Responding responding: respondingList) {
				MenuMetaData childMenuMetaData = new MenuMetaData();
				childMenuMetaData.setChartCriteria(new ChartCriteria());
				MenuItem child = new MenuItem();
				childMenuMetaData.getChartCriteria().setRespondingId(responding.getId());
				childMenuMetaData.getChartCriteria().setSurveyId(survey.getId());
				child.setData(childMenuMetaData);
				child.setLabel(responding.getDescription());
				menuItem.getChildren().add(child);
			}
			menuItems.add(menuItem);
		}
		
		return menuItems;
		
	}

	@Override
	public ChartDataWrapper getChartData(ChartCriteria chartCriteria) {
		ChartDataWrapper chartDataWrapper =  new ChartDataWrapper();
		List<SurveyResult> resultList = null;
		
		
		
		
		if(StringUtils.isEmpty(chartCriteria.getRespondingId())) {
			resultList= this.surveyResultRepository.findBySurvey(chartCriteria.getSurveyId());  
		}else {
			resultList= this.surveyResultRepository.findBySurveyAndReponsing(chartCriteria.getSurveyId(), chartCriteria.getRespondingId());  
		}
		  
		if(resultList == null || resultList.isEmpty()) {
			return null;
		}
		
		List<ColorPoolElm> colorPool = this.colorsPoolRepository.findAllWithEagerRelationships();
		
		
		Survey survey = surveyService.findOne(chartCriteria.getSurveyId()).get();
		ChartData chartData = new ChartData();
		
		
		if(chartCriteria.getChartType().toLowerCase().compareTo(ChartTypeEnum.amount.name()) == 0) {
			chartDataWrapper.setChartType("bar");
			
			//TODO : sorting by order
			for(Question question :survey.getQuestions()) {
				chartData.getLabels().add(question.getDescription());
			}
			
			for(AnswerScale scale : survey.getAnswerScales()) {
				BarChartDataSet chartDataSet  = new BarChartDataSet();
				chartDataSet.setLabel(scale.getDescription());
				for(Question question :survey.getQuestions()) {
					 Integer sum = calculateSumByQuestion(resultList, question, scale);	
					 chartDataSet.getData().add(new BigDecimal(sum));
				}
				chartData.getDatasets().add(chartDataSet);
				ColorPoolElm colorStr = colorPool.get((chartData.getDatasets().size()-1)%colorPool.size());
				chartDataSet.setBackgroundColor(colorStr.getValue());
				
			}
			
		}else if(chartCriteria.getChartType().toLowerCase().compareTo(ChartTypeEnum.percent.name()) == 0) {
			chartDataWrapper.setChartType("pie");
			PieChartDataSet pieChartDataSet = new PieChartDataSet();
			//List<Big>
			for(AnswerScale scale : survey.getAnswerScales()) {
				chartData.getLabels().add(scale.getDescription());
				for(Question question :survey.getQuestions()) {
					if(!question.getQuestionId().equals(chartCriteria.getQuestionId())) {
						continue;
					}
					Integer sum = calculateSumByQuestion(resultList, question, scale);	
					pieChartDataSet.getData().add(new BigDecimal(calculatePercentage(sum.doubleValue(), resultList.size())));
					pieChartDataSet.getBackgroundColor().add(colorPool.get((pieChartDataSet.getData().size()-1)%colorPool.size()).getValue());
				}
			}
			chartData.getDatasets().add(pieChartDataSet);
		}
		
		
		
		chartDataWrapper.setChartData(chartData);
		return chartDataWrapper;
	}
	
	
	public double calculatePercentage(double obtained, double total) {
	     return obtained*100/total;
	 }
	 
	private Integer calculateSumByQuestion(List<SurveyResult> resultList, Question question, AnswerScale scale) {
		Integer count = 0;
		for(SurveyResult surveyResult : resultList) {
			if(surveyResult.getAskedResult().getAnswers().get(question.getQuestionId()) == null) {
				continue;
			}
			
			
			if(surveyResult.getAskedResult().getAnswers().get(question.getQuestionId()) == scale.getScore()) {
				count++;
			}
		}
		
		return count;
	}
	
	
	
    
}
