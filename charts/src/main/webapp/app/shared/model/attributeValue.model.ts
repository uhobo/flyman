import { IAttributeType } from './attributeType.model';

export interface IAttributeValue {
    id?: string;
    attributeType?: IAttributeType;
    className?: string;
    value?: any;
}

export class AttributeValue implements  IAttributeValue {
    constructor(public id?: string, public  attributeType?: IAttributeType, public className?: string,  public value?: any) {}
}
