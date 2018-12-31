import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { ISurveyResult } from 'app/shared/model/survey-result.model';
import { SurveyResultService } from './survey-result.service';
import { ISurvey } from 'app/shared/model/survey.model';
import { SurveyService } from 'app/entities/survey';
import { IAsked } from 'app/shared/model/asked.model';
import { AskedService } from 'app/entities/asked';
import { IResponding } from 'app/shared/model/responding.model';
import { RespondingService } from 'app/entities/responding';
import { IAnswer } from 'app/shared/model/answer.model';
import { AnswerService } from 'app/entities/answer';

@Component({
    selector: 'jhi-survey-result-update',
    templateUrl: './survey-result-update.component.html'
})
export class SurveyResultUpdateComponent implements OnInit {
    surveyResult: ISurveyResult;
    isSaving: boolean;

    surveys: ISurvey[];

    askeds: IAsked[];

    respondings: IResponding[];

    answers: IAnswer[];
    submitDateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private surveyResultService: SurveyResultService,
        private surveyService: SurveyService,
        private askedService: AskedService,
        private respondingService: RespondingService,
        private answerService: AnswerService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ surveyResult }) => {
            this.surveyResult = surveyResult;
        });
        this.surveyService.query().subscribe(
            (res: HttpResponse<ISurvey[]>) => {
                this.surveys = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.askedService.query().subscribe(
            (res: HttpResponse<IAsked[]>) => {
                this.askeds = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.respondingService.query().subscribe(
            (res: HttpResponse<IResponding[]>) => {
                this.respondings = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.answerService.query().subscribe(
            (res: HttpResponse<IAnswer[]>) => {
                this.answers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        // if (this.surveyResult.id !== undefined) {
        //     this.subscribeToSaveResponse(this.surveyResultService.update(this.surveyResult));
        // } else {
        //     this.subscribeToSaveResponse(this.surveyResultService.create(this.surveyResult));
        // }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISurveyResult>>) {
        result.subscribe((res: HttpResponse<ISurveyResult>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSurveyById(index: number, item: ISurvey) {
        return item.id;
    }

    trackAskedById(index: number, item: IAsked) {
        return item.id;
    }

    trackRespondingById(index: number, item: IResponding) {
        return item.id;
    }

    trackAnswerById(index: number, item: IAnswer) {
        return null
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
