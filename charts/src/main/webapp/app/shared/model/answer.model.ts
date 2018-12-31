import { IQuestion } from 'app/shared/model//question.model';
import { IAnswerScale } from 'app/shared/model//answer-scale.model';

export interface IAnswer {
    question?: IQuestion;
    result?: IAnswerScale;
}

export class Answer implements IAnswer {
    constructor(public question?: IQuestion, public result?: IAnswerScale) {}
}
