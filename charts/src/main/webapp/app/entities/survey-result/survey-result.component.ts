import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISurveyResult } from 'app/shared/model/survey-result.model';
import { Principal } from 'app/core';
import { SurveyResultService } from './survey-result.service';
import {Message, TreeNode, MenuItem} from 'primeng/components/common/api';
@Component({
    selector: 'jhi-survey-result',
    templateUrl: './survey-result.component.html'
})
export class SurveyResultComponent implements OnInit, OnDestroy {
    surveyResults: ISurveyResult[];
    currentAccount: any;
    eventSubscriber: Subscription;
    menuTree: TreeNode[];
    selectedMenu: TreeNode;
    constructor(
        private surveyResultService: SurveyResultService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.surveyResultService.getAllSurveyData().then(result=> {
            this.menuTree = result.body;
            this.menuTree = [
                {label: 'View', icon: 'fa fa-search'},
                {label: 'Unselect', icon: 'fa fa-close'}
            ];
            
            console.log(this.menuTree);
        } );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        //this.registerChangeInSurveyResults();
    }

    ngOnDestroy() {
       // this.eventManager.destroy(this.eventSubscriber);
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

    nodeSelect(event) {
        //event.node = selected node
    }
}
