import { IAttribute } from './attribute.model';

export interface IPerson {
    id?: string;
    internalId?: string;
    firstName?: string;
    lastName?: string;
    personType?: number;
    attributes?: IAttribute[];
}

export class Person implements IPerson {
    constructor(
        public id?: string,
        public internalId?: string,
        public firstName?: string,
        public lastName?: string,
        public personType?: number,
        public attributes?: IAttribute[]
    ) {}
}
