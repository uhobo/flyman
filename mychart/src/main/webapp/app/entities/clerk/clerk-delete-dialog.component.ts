import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClerk } from 'app/shared/model/clerk.model';
import { ClerkService } from './clerk.service';

@Component({
  templateUrl: './clerk-delete-dialog.component.html'
})
export class ClerkDeleteDialogComponent {
  clerk?: IClerk;

  constructor(protected clerkService: ClerkService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.clerkService.delete(id).subscribe(() => {
      this.eventManager.broadcast('clerkListModification');
      this.activeModal.close();
    });
  }
}
