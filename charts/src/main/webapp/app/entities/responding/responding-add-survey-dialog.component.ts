import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResponding } from 'app/shared/model/responding.model';
import { RespondingService } from './responding.service';
import { SurveyService } from '../survey';
import { ISurvey, Survey } from 'app/shared/model/survey.model';
import { RespondingDataService } from './respondingDataService';
import { SurveyResult } from 'app/shared/model/survey-result.model';



@Component({
    selector: 'jhi-responding-add-dialog',
    templateUrl: './responding-add-survey-dialog.component.html'
})
export class RespondingAddSurveyDialogComponent {
    responding: IResponding;
    unSelectedSurvey : ISurvey[] = [];
    selectedSurvey : ISurvey[];

    constructor( public activeModal: NgbActiveModal, private eventManager: JhiEventManager, private respondingDataService: RespondingDataService) {
        
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save(){
        for(let survey of this.selectedSurvey ){
            
            this.respondingDataService.getResponding().surveys.push(survey);    
        }
        
        this.activeModal.dismiss(true);
    }

    trackAttributeById(index: number, item: ISurvey) {
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
    
}


@Component({
    selector: 'jhi-responding-delete-popup',
    template: ''
})
export class RespondingAddSurveyPopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private router: Router, private modalService: NgbModal, private respondingDataService: RespondingDataService) {
    }

    ngOnInit() {
                this.ngbModalRef = this.modalService.open(RespondingAddSurveyDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.responding = this.respondingDataService.getResponding();
                this.ngbModalRef.componentInstance.unSelectedSurvey  = this.respondingDataService.getSurveys();
                this.ngbModalRef.result.then(
                    result => {
                      //  this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        //this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
        
    
        
        
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }

   
}
