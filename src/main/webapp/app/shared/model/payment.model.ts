import { Moment } from 'moment';
import { InvoiceStatus } from 'app/shared/model/enumerations/invoice-status.model';

export interface IPayment {
  id?: number;
  invoiceNo?: string;
  paymentAmt?: number;
  invoicePaidDate?: Moment;
  payRefNo?: string;
  status?: InvoiceStatus;
  createdOn?: Moment;
  createdBy?: string;
  updatedOn?: Moment;
  updatedBy?: string;
}

export class Payment implements IPayment {
  constructor(
    public id?: number,
    public invoiceNo?: string,
    public paymentAmt?: number,
    public invoicePaidDate?: Moment,
    public payRefNo?: string,
    public status?: InvoiceStatus,
    public createdOn?: Moment,
    public createdBy?: string,
    public updatedOn?: Moment,
    public updatedBy?: string
  ) {}
}
