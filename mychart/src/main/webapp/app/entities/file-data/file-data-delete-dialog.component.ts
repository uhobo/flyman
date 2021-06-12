import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFileData } from 'app/shared/model/file-data.model';
import { FileDataService } from './file-data.service';

@Component({
  selector: 'jhi-file-data-delete-dialog',
  templateUrl: './file-data-delete-dialog.component.html'
})
export class FileDataDeleteDialogComponent {
  fileData: IFileData;

  constructor(protected fileDataService: FileDataService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear():void {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string):void {
    this.fileDataService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'fileDataListModification',
        content: 'Deleted an fileData'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-file-data-delete-popup',
  template: ''
})
export class FileDataDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit():void {
    this.activatedRoute.data.subscribe(({ fileData }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FileDataDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.fileData = fileData;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/file-data', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/file-data', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy():void {
    this.ngbModalRef = null;
  }
}
