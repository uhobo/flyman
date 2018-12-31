import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISurveyResult } from 'app/shared/model/survey-result.model';
import { SurveyResultService } from './survey-result.service';

@Component({
    selector: 'jhi-survey-result-delete-dialog',
    templateUrl: './survey-result-delete-dialog.component.html'
})
export class SurveyResultDeleteDialogComponent {
    surveyResult: ISurveyResult;

    constructor(
        private surveyResultService: SurveyResultService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.surveyResultService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'surveyResultListModification',
                content: 'Deleted an surveyResult'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-survey-result-delete-popup',
    template: ''
})
export class SurveyResultDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ surveyResult }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SurveyResultDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.surveyResult = surveyResult;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
