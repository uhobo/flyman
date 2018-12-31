import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAnswer } from 'app/shared/model/answer.model';
import { Principal } from 'app/core';
import { AnswerService } from './answer.service';

@Component({
    selector: 'jhi-answer',
    templateUrl: './answer.component.html'
})
export class AnswerComponent implements OnInit, OnDestroy {
    answers: IAnswer[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private answerService: AnswerService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.answerService.query().subscribe(
            (res: HttpResponse<IAnswer[]>) => {
                this.answers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAnswers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAnswer) {
        return item.id;
    }

    registerChangeInAnswers() {
        this.eventSubscriber = this.eventManager.subscribe('answerListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
