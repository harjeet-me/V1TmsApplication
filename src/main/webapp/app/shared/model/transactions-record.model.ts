import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';
import { IAccounts } from 'app/shared/model/accounts.model';
import { IInvoice } from 'app/shared/model/invoice.model';
import { TransactionType } from 'app/shared/model/enumerations/transaction-type.model';
import { TxStatus } from 'app/shared/model/enumerations/tx-status.model';
import { CURRENCY } from 'app/shared/model/enumerations/currency.model';

export interface ITransactionsRecord {
  id?: number;
  txType?: TransactionType;
  description?: string;
  txAmmount?: number;
  txRefNo?: string;
  txCreatedBy?: string;
  txCreatedDate?: Moment;
  txCompletedBy?: string;
  txCompletedDate?: Moment;
  status?: TxStatus;
  txDocContentType?: string;
  txDoc?: any;
  currency?: CURRENCY;
  remarks?: string;
  createdOn?: Moment;
  createdBy?: string;
  updatedOn?: Moment;
  updatedBy?: string;
  customer?: ICustomer;
  account?: IAccounts;
  invoice?: IInvoice;
}

export class TransactionsRecord implements ITransactionsRecord {
  constructor(
    public id?: number,
    public txType?: TransactionType,
    public description?: string,
    public txAmmount?: number,
    public txRefNo?: string,
    public txCreatedBy?: string,
    public txCreatedDate?: Moment,
    public txCompletedBy?: string,
    public txCompletedDate?: Moment,
    public status?: TxStatus,
    public txDocContentType?: string,
    public txDoc?: any,
    public currency?: CURRENCY,
    public remarks?: string,
    public createdOn?: Moment,
    public createdBy?: string,
    public updatedOn?: Moment,
    public updatedBy?: string,
    public customer?: ICustomer,
    public account?: IAccounts,
    public invoice?: IInvoice
  ) {}
}
