import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';
import { IAccounts } from 'app/shared/model/accounts.model';
import { TransactionType } from 'app/shared/model/enumerations/transaction-type.model';
import { TxStatus } from 'app/shared/model/enumerations/tx-status.model';

export interface ITransactionsRecord {
  id?: number;
  txType?: TransactionType;
  description?: string;
  txAmmount?: number;
  status?: TxStatus;
  createdOn?: Moment;
  createdBy?: string;
  updatedOn?: Moment;
  updatedBy?: string;
  customer?: ICustomer;
  account?: IAccounts;
}

export class TransactionsRecord implements ITransactionsRecord {
  constructor(
    public id?: number,
    public txType?: TransactionType,
    public description?: string,
    public txAmmount?: number,
    public status?: TxStatus,
    public createdOn?: Moment,
    public createdBy?: string,
    public updatedOn?: Moment,
    public updatedBy?: string,
    public customer?: ICustomer,
    public account?: IAccounts
  ) {}
}
