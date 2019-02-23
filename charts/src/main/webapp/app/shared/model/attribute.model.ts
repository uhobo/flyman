import { AttributeType } from './attributeType.model';
import { IAttributeValue } from './attributeValue.model';



export interface IAttribute {
    id?: string;
    name?: string;
    attributeType?: AttributeType;
    isClosedValueList?: boolean;
    valuesList?: IAttributeValue[];
   
}

export class Attribute implements IAttribute {
    
    
    constructor(public id?: string, public name?: string, public attributeType?: AttributeType, public isClosedValueList: boolean = false, public valuesList?: IAttributeValue[]) {}
    
     
}
