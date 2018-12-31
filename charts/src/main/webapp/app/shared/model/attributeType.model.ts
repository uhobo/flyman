import { IAttributeValue } from './attributeValue.model';

export interface IAttributeType {
    id?: string;
    description?: string;
    enableList?: boolean;
    className?: string;
    valuesList?: IAttributeValue[];
}

export class AttributeType implements IAttributeType {
    constructor(public id?: string, public description?: string, public enableList?: boolean, public className?: string, valuesList?: IAttributeValue[]) {}
}
