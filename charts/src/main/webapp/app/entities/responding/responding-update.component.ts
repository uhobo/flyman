import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { IResponding } from 'app/shared/model/responding.model';
import { RespondingService } from './responding.service';
import { IAttribute } from 'app/shared/model/attribute.model';
import { AttributeService } from 'app/entities/attribute';
import { SurveyService } from '../survey/survey.service';
import { ISurvey } from 'app/shared/model/survey.model';
import { ModelDataService } from 'app/shared/util/modelDataService';
import { RespondingDataService } from './respondingDataService';
import { RespondingAddSurveyDialogComponent } from './responding-add-survey-dialog.component';
import { AttributeAddDialogComponent } from '../attribute/attribute-add-dialog.component';




@Component({
    selector: 'jhi-responding-update',
    templateUrl: './responding-update.component.html'
})
export class RespondingUpdateComponent implements OnInit {
    responding: IResponding;
    isSaving: boolean;

    attributes: IAttribute[];
    surveys: ISurvey[];
    //unselectedSurveys: ISurvey[] = [];

    constructor(
        private jhiAlertService: JhiAlertService,
        private respondingService: RespondingService,
        private attributeService: AttributeService,
        private activatedRoute: ActivatedRoute,
        private surveyService: SurveyService,
        private modelDataService: ModelDataService,
        private respondingDataService: RespondingDataService,
        private modalService: NgbModal
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ responding }) => {
            this.responding = responding;
        });
        this.attributeService.query().subscribe(
            (res: HttpResponse<IAttribute[]>) => {
                this.attributes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.surveyService.query().subscribe(
            (res: HttpResponse<ISurvey[]>) => {
                this.surveys = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.responding.id !== undefined) {
            this.subscribeToSaveResponse(this.respondingService.update(this.responding));
        } else {
            this.subscribeToSaveResponse(this.respondingService.create(this.responding));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IResponding>>) {
        result.subscribe((res: HttpResponse<IResponding>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError(res));
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError(res: HttpErrorResponse) {
        console.log(res);
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAttributeById(index: number, item: IAttribute) {
        return item.id;
    }

    trackSurveyById(index: number, item: ISurvey){
        
        return item.id;
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

    openAddSurveyPopUp(){
        this.respondingDataService.setResponding(this.responding);
        let arr = new Array<ISurvey>();
        for(const survey of this.surveys){
            arr.push(survey);
              for(const mySurvey of this.responding.surveys){
                  arr = arr.filter(obj => obj.id !== mySurvey.id );
              }
        }
        this.respondingDataService.setSurveys(arr);

        const modalRef = this.modalService.open(RespondingAddSurveyDialogComponent );

        modalRef.componentInstance.responding = this.respondingDataService.getResponding();
        modalRef.componentInstance.unSelectedSurvey  = this.respondingDataService.getSurveys();
        modalRef.result.then(
            result => {
              console.log(result);
            },
            reason => {
                console.log(reason);
            }
        );
    }


    openAddAttributePopUp(){
        let arr = new Array<IAttribute>();
        for(const attribute of this.attributes){
            arr.push(attribute);
              for(const responseAttribute of this.responding.attributes){
                  arr = arr.filter(obj => obj.id !== responseAttribute.id );
              }
        }


        this.respondingDataService.setResponding(this.responding);
        const modalRef = this.modalService.open(AttributeAddDialogComponent);
        modalRef.componentInstance.unSelectedAttributes = arr;
         
    }
}
