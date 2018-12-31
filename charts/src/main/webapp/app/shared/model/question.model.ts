import { IAnswerScale } from './answer-scale.model';

export interface IQuestion {
    questionId?: number;
    order?: number;
    description?: string;
}

export class Question implements IQuestion {
    constructor(public questionId?: number, public order?: number, public description?: string) {}
}
