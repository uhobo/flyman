import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResponding } from 'app/shared/model/responding.model';
import { RespondingService } from './responding.service';

@Component({
    selector: 'jhi-responding-delete-dialog',
    templateUrl: './responding-delete-dialog.component.html'
})
export class RespondingDeleteDialogComponent {
    responding: IResponding;

    constructor(private respondingService: RespondingService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.respondingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'respondingListModification',
                content: 'Deleted an responding'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-responding-delete-popup',
    template: ''
})
export class RespondingDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ responding }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RespondingDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.responding = responding;
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
