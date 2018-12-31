export interface IAttributeValue {
    id?: string;
    className?: string;
    value?: any;
}

export class AttributeValue implements  IAttributeValue {
    constructor(public id?: string, public className?: string,  public value?: any) {}
}
