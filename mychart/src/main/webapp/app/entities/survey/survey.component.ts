import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISurvey } from 'app/shared/model/survey.model';
import { SurveyService } from './survey.service';
import { SurveyDeleteDialogComponent } from './survey-delete-dialog.component';

@Component({
  selector: 'jhi-survey',
  templateUrl: './survey.component.html'
})
export class SurveyComponent implements OnInit, OnDestroy {
  surveys?: ISurvey[];
  eventSubscriber?: Subscription;

  constructor(protected surveyService: SurveyService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.surveyService.query().subscribe((res: HttpResponse<ISurvey[]>) => {
      this.surveys = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSurveys();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISurvey): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSurveys(): void {
    this.eventSubscriber = this.eventManager.subscribe('surveyListModification', () => this.loadAll());
  }

  delete(survey: ISurvey): void {
    const modalRef = this.modalService.open(SurveyDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.survey = survey;
  }
}
