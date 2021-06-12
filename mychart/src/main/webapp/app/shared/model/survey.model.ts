export interface ISurvey {
  id?: string;
  title?: string;
  description?: string;
}

export class Survey implements ISurvey {
  constructor(public id?: string, public title?: string, public description?: string) {}
}
