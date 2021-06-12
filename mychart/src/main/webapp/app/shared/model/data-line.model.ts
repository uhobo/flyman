export interface IDataLine {
    lineNum?: number;
    data?: any[];
}

export class DataLine implements IDataLine {
    constructor(public lineNum?: number, public data?: any[], public disabled?:boolean) {}
  }
  