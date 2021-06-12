export interface ISeriesItem {
    title?: string;
    value?: number;
}

export class SeriesItem implements ISeriesItem {
    constructor(public title?: string, public value?: number) {}
  }
  