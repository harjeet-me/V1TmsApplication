import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IAccounts {
  id?: number;
  balance?: number;
  over30?: number;
  over60?: number;
  over90?: number;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  customer?: ICustomer;
}

export class Accounts implements IAccounts {
  constructor(
    public id?: number,
    public balance?: number,
    public over30?: number,
    public over60?: number,
    public over90?: number,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public customer?: ICustomer
  ) {}
}
