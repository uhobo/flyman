import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IResponding } from 'app/shared/model/responding.model';
import { Principal } from 'app/core';
import { RespondingService } from './responding.service';

@Component({
    selector: 'jhi-responding',
    templateUrl: './responding.component.html'
})
export class RespondingComponent implements OnInit, OnDestroy {
    respondings: IResponding[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private respondingService: RespondingService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.respondingService.query().subscribe(
            (res: HttpResponse<IResponding[]>) => {
                this.respondings = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRespondings();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IResponding) {
        return item.id;
    }

    registerChangeInRespondings() {
        this.eventSubscriber = this.eventManager.subscribe('respondingListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
