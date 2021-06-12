import { FileData } from './file-data.model';

export interface IChartDisplay {
    title?: string;
    fileDataList?: FileData[];
    keyType?: number;
    categoryType?: number;

}

export class ChartDisplay implements IChartDisplay {
    constructor(public title?: string) {}
  }