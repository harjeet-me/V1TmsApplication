import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';
import { ITransactionsRecord } from 'app/shared/model/transactions-record.model';

export interface IAccounts {
  id?: number;
  balance?: number;
  over30?: number;
  over60?: number;
  over90?: number;
  createdOn?: Moment;
  createdBy?: string;
  updatedOn?: Moment;
  updatedBy?: string;
  customer?: ICustomer;
  transactionsRecords?: ITransactionsRecord[];
}

export class Accounts implements IAccounts {
  constructor(
    public id?: number,
    public balance?: number,
    public over30?: number,
    public over60?: number,
    public over90?: number,
    public createdOn?: Moment,
    public createdBy?: string,
    public updatedOn?: Moment,
    public updatedBy?: string,
    public customer?: ICustomer,
    public transactionsRecords?: ITransactionsRecord[]
  ) {}
}
