import { Moment } from 'moment';
import { DataHeader } from './data-header.model';
import { DataLine } from '../model/data-line.model';
import { SeriesItem } from './series-item.model';
import { InfoData } from './info-data.model';


export interface  IFileData {
  id?: string;
  fileName?: string;
  title?: string;
  timestamp?: Moment;
  topicColIndex?: number;
  groupColIndex?: number;
  seriesList?: SeriesItem[];
  headers?: DataHeader[];
  dataLines?: DataLine[];
  infoDataList?: InfoData[];
}

export class FileData implements IFileData {
  constructor(public id?: string, public fileName?: string, public title?: string, public timestamp?: Moment, public topicColIndex?: number, public groupColIndex?: number, public seriesList?: SeriesItem[], public headers?: DataHeader[], public dataLines?: DataLine[], public infoDataList?: InfoData[]) {}
}
