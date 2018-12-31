import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnswerScale } from 'app/shared/model/answer-scale.model';

@Component({
    selector: 'jhi-answer-scale-detail',
    templateUrl: './answer-scale-detail.component.html'
})
export class AnswerScaleDetailComponent implements OnInit {
    answerScale: IAnswerScale;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ answerScale }) => {
            this.answerScale = answerScale;
        });
    }

    previousState() {
        window.history.back();
    }
}
