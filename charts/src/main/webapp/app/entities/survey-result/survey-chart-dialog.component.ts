import { ISurveyResult, SurveyResult } from 'app/shared/model/survey-result.model';
import { SurveyChart, ChartDataSet } from 'app/shared/model/surveyChart.model';
import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ModelDataService } from 'app/shared/util/modelDataService';
import { THROW_IF_NOT_FOUND } from '@angular/core/src/di/injector';
import { CalculatorUtil } from 'app/shared/util/calculatorUtil';
import { ISurvey } from 'app/shared/model/survey.model';

@Component({
    selector: 'jhi-survey-chart-dialog',
    templateUrl: './survey-chart-dialog.component.html'
})
export class ChartSurveyDialogComponent implements OnInit{
    surveyResultArr: ISurveyResult[] = new Array<ISurveyResult>();
    surveyChart: SurveyChart = new SurveyChart();
    
    constructor( public activeModal: NgbActiveModal, public modelDataService: ModelDataService ){
    
        this.surveyResultArr = this.modelDataService.getData();
        this.surveyChart.datasets = new Array<ChartDataSet>();
        let chartDataSet = new ChartDataSet();
        this.surveyChart.datasets[0] = chartDataSet;
    
        this.surveyResultArr[0].survey.questions.forEach((element, index) => {
            this.surveyChart.datasets[0].data[index]  = CalculatorUtil.calcAverageQuestion(this.surveyResultArr, element.questionId);
            this.surveyChart.labels[index]  = element.description;
        });
    
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    ngOnInit() {
        
    }
}
