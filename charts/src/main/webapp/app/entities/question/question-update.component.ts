import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from './question.service';
import { IAnswerScale } from 'app/shared/model/answer-scale.model';
import { AnswerScaleService } from 'app/entities/answer-scale';

@Component({
    selector: 'jhi-question-update',
    templateUrl: './question-update.component.html'
})
export class QuestionUpdateComponent implements OnInit {
    question: IQuestion;
    isSaving: boolean;

    answerscales: IAnswerScale[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private questionService: QuestionService,
        private answerScaleService: AnswerScaleService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ question }) => {
            this.question = question;
        });
        this.answerScaleService.query().subscribe(
            (res: HttpResponse<IAnswerScale[]>) => {
                this.answerscales = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.question.id !== undefined) {
            this.subscribeToSaveResponse(this.questionService.update(this.question));
        } else {
            this.subscribeToSaveResponse(this.questionService.create(this.question));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IQuestion>>) {
        result.subscribe((res: HttpResponse<IQuestion>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAnswerScaleById(index: number, item: IAnswerScale) {
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
