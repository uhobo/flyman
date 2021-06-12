import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISurvey } from 'app/shared/model/survey.model';
import { SurveyService } from './survey.service';

@Component({
  templateUrl: './survey-delete-dialog.component.html'
})
export class SurveyDeleteDialogComponent {
  survey?: ISurvey;

  constructor(protected surveyService: SurveyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.surveyService.delete(id).subscribe(() => {
      this.eventManager.broadcast('surveyListModification');
      this.activeModal.close();
    });
  }
}
