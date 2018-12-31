import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISurvey } from 'app/shared/model/survey.model';
import { SurveyDataService } from 'app/shared/util/SurveyDataService';

@Component({
    selector: 'jhi-survey-detail',
    templateUrl: './survey-detail.component.html'
})
export class SurveyDetailComponent implements OnInit {
    survey: ISurvey;

    constructor(private activatedRoute: ActivatedRoute,  private surveyDataService: SurveyDataService) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ survey }) => {
            this.survey = survey;
        });
    }

    previousState() {
        window.history.back();
    }
}
