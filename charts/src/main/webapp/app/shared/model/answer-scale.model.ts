export interface IAnswerScale {
    id?: string;
    description?: string;
    score?: number;
}

export class AnswerScale implements IAnswerScale {
    constructor(public description?: string, public score?: number) {}
}
