import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISurveyResult } from 'app/shared/model/survey-result.model';
import { Principal } from 'app/core';
import { SurveyResultService } from './survey-result.service';

@Component({
    selector: 'jhi-survey-result',
    templateUrl: './survey-result.component.html'
})
export class SurveyResultComponent implements OnInit, OnDestroy {
    surveyResults: ISurveyResult[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private surveyResultService: SurveyResultService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.surveyResultService.query().subscribe(
            (res: HttpResponse<ISurveyResult[]>) => {
                this.surveyResults = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSurveyResults();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISurveyResult) {
        return item.id;
    }

    registerChangeInSurveyResults() {
        this.eventSubscriber = this.eventManager.subscribe('surveyResultListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
