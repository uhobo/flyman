import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnswerScale } from 'app/shared/model/answer-scale.model';
import { AnswerScaleService } from './answer-scale.service';

@Component({
    selector: 'jhi-answer-scale-delete-dialog',
    templateUrl: './answer-scale-delete-dialog.component.html'
})
export class AnswerScaleDeleteDialogComponent {
    answerScale: IAnswerScale;

    constructor(
        private answerScaleService: AnswerScaleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.answerScaleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'answerScaleListModification',
                content: 'Deleted an answerScale'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-answer-scale-delete-popup',
    template: ''
})
export class AnswerScaleDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ answerScale }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AnswerScaleDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.answerScale = answerScale;
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
