import { IQuestion } from './question.model';
import { AnswerScale } from './answer-scale.model';

export interface ISurvey {
    id?: string;
    title?: string;
    description?: string;

    answerScales?: AnswerScale[];

    questions?: IQuestion[];

    getSurveyLabels();
}

export class Survey implements ISurvey {
    constructor(public id?: string, public title?: string, public description?: string, public questions?: IQuestion[], public answerScales?: AnswerScale[]) {
        
    }

    public getSurveyLabels(){
        return this.questions.map(q => { return q.description});
    }

}
