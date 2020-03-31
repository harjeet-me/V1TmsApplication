export interface IAccountHistory {
  id?: number;
  enityName?: string;
  entityLink?: string;
  action?: string;
}

export class AccountHistory implements IAccountHistory {
  constructor(public id?: number, public enityName?: string, public entityLink?: string, public action?: string) {}
}
