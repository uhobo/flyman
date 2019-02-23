import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISurveyResult } from 'app/shared/model/survey-result.model';
import { Principal } from 'app/core';
import { SurveyResultService } from './survey-result.service';
import {Message, TreeNode, MenuItem, SelectItem} from 'primeng/components/common/api';
import { ChartDataWrapper } from 'app/shared/model/surveyChart.model';
import { ChartCriteria } from 'app/shared/model/chartcriteria.model';
import { MenuMetaData } from 'app/shared/model/Menumetadata';
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
    chartTypeList: SelectItem[];
    selectedCharType: string = "amount";
    categoryList:  SelectItem[];
    selectedcategory: number;

    chartDataWrapper: ChartDataWrapper = new ChartDataWrapper(); 
    constructor(
        private surveyResultService: SurveyResultService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
        this.chartTypeList = [
            {label:'כמות', value:'amount'},
            {label:'אחוזים', value:'percent'}];

    }

    loadAll() {
        this.surveyResultService.getAllSurveyData().then(result=> {
            this.menuTree = result.body;
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

    nodeSelect(event :any) {
        //event.node = selected node
        console.log(event.node.data);
        let menuMetaData: MenuMetaData = event.node.data;
        
        if(menuMetaData.chartSelectList !== undefined && menuMetaData.chartSelectList !== null){
            this.categoryList = menuMetaData.chartSelectList;
        }else if(event.node.data.parent != undefined){
            let parentMenuMetaData: MenuMetaData =  event.node.data.parent.data;
            if(parentMenuMetaData.chartSelectList !== undefined ){
                this.categoryList = menuMetaData.chartSelectList;
            }
        }
        //let chartCriteria : ChartCriteria = event.node.data;
        if(menuMetaData.chartCriteria === undefined){
            menuMetaData.chartCriteria = new ChartCriteria();
        }
        menuMetaData.chartCriteria.chartType = this.selectedCharType;
        menuMetaData.chartCriteria.questionId = this.selectedcategory;
        this.surveyResultService.getChartData(menuMetaData.chartCriteria).then(result => {
            this.chartDataWrapper = result.body;
            console.log(this.chartDataWrapper);
        });
    }

    nodeUnselect(event: any) {
    }

}
