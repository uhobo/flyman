export interface IDataHeader {
    title?: string;
    index?: number;
    show?: boolean;
    disabled?: boolean;
}

export class DataHeader implements IDataHeader {
    constructor(public title?: string, public index?: number, public show?: boolean, public disabled?:boolean) {}
  }
  