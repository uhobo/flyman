import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAsked } from 'app/shared/model/asked.model';
import { Principal } from 'app/core';
import { AskedService } from './asked.service';

@Component({
    selector: 'jhi-asked',
    templateUrl: './asked.component.html'
})
export class AskedComponent implements OnInit, OnDestroy {
    askeds: IAsked[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private askedService: AskedService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.askedService.query().subscribe(
            (res: HttpResponse<IAsked[]>) => {
                this.askeds = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAskeds();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAsked) {
        return item.id;
    }

    registerChangeInAskeds() {
        this.eventSubscriber = this.eventManager.subscribe('askedListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
