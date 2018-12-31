package com.geller.charts.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.geller.charts.ChartsApp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChartsApp.class)
public class SurveyServiceIntTest {

	@Autowired
    private SurveyService surveyService;

	
	 @Test
	 public void initSurveyData() {
		 
	 }
}
