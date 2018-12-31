import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISurvey } from 'app/shared/model/survey.model';
import { Principal } from 'app/core';
import { SurveyService } from './survey.service';
import { SurveyDataService } from "app/shared/util/SurveyDataService";

@Component({
    selector: 'jhi-survey',
    templateUrl: './survey.component.html'
})
export class SurveyComponent implements OnInit, OnDestroy {
    surveys: ISurvey[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private surveyService: SurveyService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private surveyDataService: SurveyDataService
    ) {}

    loadAll() {
        this.surveyService.query().subscribe(
            (res: HttpResponse<ISurvey[]>) => {
                this.surveys = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSurveys();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISurvey) {
        return item.id;
    }

    registerChangeInSurveys() {
        this.eventSubscriber = this.eventManager.subscribe('surveyListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
