import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISurvey, Survey } from 'app/shared/model/survey.model';
import { SurveyService } from './survey.service';
import { IQuestion } from 'app/shared/model/question.model';
import { Question } from 'app/shared/model/question.model';
import { SurveyDataService } from 'app/shared/util/SurveyDataService';

@Component({
    selector: 'jhi-survey-update',
    templateUrl: './survey-update.component.html',
    providers: []
})
export class SurveyUpdateComponent implements OnInit {
    survey: ISurvey;
    isSaving: boolean;

    questions: IQuestion[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private surveyService: SurveyService,
        private activatedRoute: ActivatedRoute,
        private surveyDataService: SurveyDataService
    ) {}

    ngOnInit() {
        this.isSaving = false;
       // this.activatedRoute.data.subscribe(({ survey }) => {
      //      this.survey = survey;
      //  });
       if(this.surveyDataService.getData() != undefined){
            this.survey = this.surveyDataService.getData();
        }else{
            this.survey = new Survey();
        }
    }

    previousState() {
        window.history.back();
    }

    addQuestion(){
        let nextIndex  = 1;
        if(this.survey.questions !== undefined && this.survey.questions.length > 0){
            nextIndex = this.survey.questions[this.survey.questions.length-1].order +1;
        }
        const newValue = new Question(nextIndex, nextIndex, '');
        if(this.survey.questions === undefined){
            this.survey.questions  = new Array<IQuestion>();
        }
        this.survey.questions.push(newValue);

    }

    save() {
        this.isSaving = true;
        if (this.survey.id !== undefined) {
            this.subscribeToSaveResponse(this.surveyService.update(this.survey));
        } else {
            this.subscribeToSaveResponse(this.surveyService.create(this.survey));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISurvey>>) {
        result.subscribe((res: HttpResponse<ISurvey>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }


    // getSelected(selectedVals: Array<any>, option: any) {
    //     if (selectedVals) {
    //         for (let i = 0; i < selectedVals.length; i++) {
    //             if (option.id === selectedVals[i].id) {
    //                 return selectedVals[i];
    //             }
    //         }
    //     }
    //     return option;
    // }
}
