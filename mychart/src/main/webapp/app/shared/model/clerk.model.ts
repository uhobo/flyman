export interface IClerk {
  id?: string;
  firstName?: string;
  lastName?: string;
}

export class Clerk implements IClerk {
  constructor(public id?: string, public firstName?: string, public lastName?: string) {}
}
