import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IClerk } from 'app/shared/model/clerk.model';
import { ClerkService } from './clerk.service';
import { ClerkDeleteDialogComponent } from './clerk-delete-dialog.component';

@Component({
  selector: 'jhi-clerk',
  templateUrl: './clerk.component.html'
})
export class ClerkComponent implements OnInit, OnDestroy {
  clerks?: IClerk[];
  eventSubscriber?: Subscription;

  constructor(protected clerkService: ClerkService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.clerkService.query().subscribe((res: HttpResponse<IClerk[]>) => {
      this.clerks = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInClerks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IClerk): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInClerks(): void {
    this.eventSubscriber = this.eventManager.subscribe('clerkListModification', () => this.loadAll());
  }

  delete(clerk: IClerk): void {
    const modalRef = this.modalService.open(ClerkDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.clerk = clerk;
  }
}
