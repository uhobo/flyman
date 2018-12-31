import { IAttribute } from './attribute.model';

export interface IAsked {
    id?: string;
    internalId?:string;
    description?: string;
    attributes?: IAttribute[];
}

export class Asked implements IAsked {
    constructor(public id?: string, public internalId?: string, public description?: string, public attributes?: IAttribute[]) {}
}
