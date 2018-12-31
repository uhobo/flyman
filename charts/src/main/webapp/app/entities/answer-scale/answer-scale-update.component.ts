import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISurvey } from 'app/shared/model/survey.model';
import { IAnswerScale } from 'app/shared/model/answer-scale.model';
import { AnswerScale } from 'app/shared/model/answer-scale.model';
import { SurveyDataService } from 'app/shared/util/SurveyDataService';
import { duplicateAnswerScaleValidator } from 'app/shared/util/custom-validator';

@Component({
    selector: 'jhi-answer-scale-update',
    templateUrl: './answer-scale-update.component.html'
})
export class AnswerScaleUpdateComponent implements OnInit {
    survey: ISurvey;
    answerScaleArray: IAnswerScale[] = [];
    isSaving: boolean;

    constructor( private activatedRoute: ActivatedRoute, private surveyDataService: SurveyDataService) {
        console.log(surveyDataService.survey.id);
        this.survey = surveyDataService.survey;
        if(this.survey.answerScales === undefined || this.survey.answerScales.length === 0){
            const newValue = new AnswerScale('', 1);
            this.answerScaleArray.push(newValue);
        }else{
            this.answerScaleArray = Object.assign([], this.survey.answerScales);
        }
    }

    ngOnInit() {
        this.isSaving = false;
        
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.survey.answerScales = this.answerScaleArray;
        this.surveyDataService.setData(this.survey);
        this.previousState();
    }

    removeAnswerScale(score:IAnswerScale){
       const index = this.answerScaleArray.indexOf(score, 0);
        if (index > -1) {
            this.answerScaleArray.splice(index, 1);
        }
    }

    addAnswerScale(){
        let nextIndex  = 1;
        if(this.answerScaleArray !== undefined && this.answerScaleArray.length > 0){
            nextIndex = this.answerScaleArray[this.answerScaleArray.length-1].score +1;
        }
        const newValue = new AnswerScale('', nextIndex);
        this.answerScaleArray.push(newValue);
        this.answerScaleArray.sort((obj1,obj2) => {
                if(obj1.score > obj2.score){
                    return 1;
                }
                if(obj1.score < obj2.score){
                    return -1;
                }
                return 0 ;
        });
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
