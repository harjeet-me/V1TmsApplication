import { Moment } from 'moment';

export interface IAccountHistory {
  id?: number;
  enityName?: string;
  entityLink?: string;
  action?: string;
  createdOn?: Moment;
  createdBy?: string;
  updatedOn?: Moment;
  updatedBy?: string;
}

export class AccountHistory implements IAccountHistory {
  constructor(
    public id?: number,
    public enityName?: string,
    public entityLink?: string,
    public action?: string,
    public createdOn?: Moment,
    public createdBy?: string,
    public updatedOn?: Moment,
    public updatedBy?: string
  ) {}
}
