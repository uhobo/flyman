import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISurveyResult, SurveyResult } from 'app/shared/model/survey-result.model';
import { ISurvey } from 'app/shared/model/survey.model';
import { formArrayNameProvider } from '@angular/forms/src/directives/reactive_directives/form_group_name';
import { NgForm } from '@angular/forms';
import { SurveyResultService } from './survey-result.service';
import { JhiAlertService } from 'ng-jhipster/src/service';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ChartSurveyDialogComponent } from './survey-chart-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModelDataService } from 'app/shared/util/modelDataService';

@Component({
    selector: 'jhi-survey-result-detail',
    templateUrl: './survey-result-detail.component.html'
})
export class SurveyResultDetailComponent implements OnInit {
    surveyResults: ISurveyResult[];
    survey: ISurvey;
    questionIndex: number;
    totalItems: number;
    itemsPerPage: any;
    previousPage: any;

    constructor(private activatedRoute: ActivatedRoute,
        private surveyResultService: SurveyResultService,
        private jhiAlertService: JhiAlertService,
        private modalService: NgbModal,
        private modelDataService: ModelDataService) {
            this.itemsPerPage = 1;

        }

    ngOnInit() {
        
        this.questionIndex = 0;
        this.activatedRoute.data.subscribe(({ surveyResults }) => {
            this.surveyResults = surveyResults;
            if(this.surveyResults.length > 0){
                this.survey = this.surveyResults[0].survey;
                this.totalItems = this.survey.questions.length;
                this.previousPage =  this.questionIndex;
            }
        });
    }

  

    onSelectionChange(surveyResult : SurveyResult, score : number,  questionId: number){
       // console.log('onSelectionChange: id= ' + surveyResult.askedResult.asked.id + ', score='  + score);
        surveyResult.askedResult.answers[questionId]=  score;
       // console.log('The entry is =' + surveyResult.askedResult.answers[questionId]);
        
    }

    isChecked(surveyResult : SurveyResult , score : number, questionId: number) : boolean{
       
       // console.log('surveyResult.askedResult.answers.size=' + surveyResult.askedResult.answers);
        
         if( surveyResult.askedResult.answers === undefined){
            console.log('surveyResult.askedResult.answers.size=' + surveyResult.askedResult.answers);
            return false;
        }
        
        return  surveyResult.askedResult.answers[questionId] === score;
    }
    previousState() {
        window.history.back();
    }

    save() {
           
        this.subscribeToSaveResponse(this.surveyResultService.update(this.surveyResults));
        
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISurveyResult[]>>) {
        result.subscribe((res: HttpResponse<ISurveyResult[]>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        
       // this.previousState();
    }

    private onSaveError() {
       // this.isSaving = false;
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
        }
    }

    showChart(){
        this.modelDataService.setData(this.surveyResults);
        const modalRef = this.modalService.open(ChartSurveyDialogComponent );

        modalRef.result.then(
            result => {
              console.log(result);
            },
            reason => {
                console.log(reason);
            }
        );
    }

}
