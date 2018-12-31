import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

import { IResponding } from 'app/shared/model/responding.model';
import { RespondingService } from './responding.service';
import { JhiAlertService } from 'ng-jhipster';
import { Survey } from 'app/shared/model/survey.model';
import { RespondingSurveyInput } from 'app/shared/model/respondingSurveyInput.model';
import { SurveyResultService } from '../survey-result/survey-result.service';
import { ISurveyResult, SurveyResult } from 'app/shared/model/survey-result.model';

@Component({
    selector: 'jhi-responding-detail',
    templateUrl: './responding-detail.component.html'
})
export class RespondingDetailComponent implements OnInit {
    responding: IResponding;
    surveyResults: ISurveyResult[];
    

    constructor(private activatedRoute: ActivatedRoute,  private respondingService: RespondingService, private  surveyResultService : SurveyResultService ,private jhiAlertService: JhiAlertService) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ responding }) => {
            this.responding = responding;
            
        });

        this.surveyResultService.findByResponding(this.responding.id).subscribe(
            (res: HttpResponse<ISurveyResult[]>) =>{
                this.surveyResults = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    publishSurvey(mySurvey: Survey){
        let repondingSurveyInput = new  RespondingSurveyInput();
        repondingSurveyInput.survey = mySurvey;
        repondingSurveyInput.responding = this.responding;

        this.respondingService.publish(repondingSurveyInput).subscribe(
            (res: HttpResponse<ISurveyResult[]>) => {
                this.surveyResults  = res.body;
               // this.jhiAlertService.info("Added " + res.body);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        
    }
    isPublishSurvey(surveyId: String) : boolean{
        let result: boolean;

        if(this.surveyResults === null || this.surveyResults === undefined || this.surveyResults.length === 0){
            return false;
        }
        result = this.surveyResults.findIndex( surveyResult => surveyResult.survey.id === surveyId) > -1;
        return result;
    }
    
    getSurveyResultId(surveyId: String) : String{
        if(this.surveyResults === null || this.surveyResults === undefined){
            return   "0";
        }
        const surveyResult = this.surveyResults.find(surveyResult => surveyResult.survey.id === surveyId);
        if(surveyResult !== null && surveyResult!== undefined){
            return surveyResult.id;
        }
        return "0";
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }


}
