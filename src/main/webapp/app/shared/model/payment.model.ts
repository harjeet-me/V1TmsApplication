import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';
import { PayMode } from 'app/shared/model/enumerations/pay-mode.model';

export interface IPayment {
  id?: number;
  invoiceNo?: string;
  payDate?: Moment;
  payRefNo?: string;
  mode?: PayMode;
  ammount?: number;
  unusedAmmount?: number;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  customer?: ICustomer;
}

export class Payment implements IPayment {
  constructor(
    public id?: number,
    public invoiceNo?: string,
    public payDate?: Moment,
    public payRefNo?: string,
    public mode?: PayMode,
    public ammount?: number,
    public unusedAmmount?: number,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public customer?: ICustomer
  ) {}
}
