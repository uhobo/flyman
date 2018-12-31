import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAnswerScale } from 'app/shared/model/answer-scale.model';
import { Principal } from 'app/core';
import { AnswerScaleService } from './answer-scale.service';

@Component({
    selector: 'jhi-answer-scale',
    templateUrl: './answer-scale.component.html'
})
export class AnswerScaleComponent implements OnInit, OnDestroy {
    answerScales: IAnswerScale[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private answerScaleService: AnswerScaleService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.answerScaleService.query().subscribe(
            (res: HttpResponse<IAnswerScale[]>) => {
                this.answerScales = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAnswerScales();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAnswerScale) {
        return item.id;
    }

    registerChangeInAnswerScales() {
        this.eventSubscriber = this.eventManager.subscribe('answerScaleListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
