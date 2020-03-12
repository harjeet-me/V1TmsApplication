import { Moment } from 'moment';
import { InvoiceStatus } from 'app/shared/model/enumerations/invoice-status.model';

export interface IInvoiceHistory {
  id?: number;
  status?: InvoiceStatus;
  comment?: string;
  createdOn?: Moment;
  createdBy?: string;
  updatedOn?: Moment;
  updatedBy?: string;
  previous?: IInvoiceHistory;
  next?: IInvoiceHistory;
}

export class InvoiceHistory implements IInvoiceHistory {
  constructor(
    public id?: number,
    public status?: InvoiceStatus,
    public comment?: string,
    public createdOn?: Moment,
    public createdBy?: string,
    public updatedOn?: Moment,
    public updatedBy?: string,
    public previous?: IInvoiceHistory,
    public next?: IInvoiceHistory
  ) {}
}
