import { Moment } from 'moment';
import { IInvoice } from 'app/shared/model/invoice.model';
import { InvoiceStatus } from 'app/shared/model/enumerations/invoice-status.model';

export interface IInvoiceHistory {
  id?: number;
  status?: InvoiceStatus;
  comment?: string;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  previous?: IInvoiceHistory;
  next?: IInvoiceHistory;
  invoice?: IInvoice;
}

export class InvoiceHistory implements IInvoiceHistory {
  constructor(
    public id?: number,
    public status?: InvoiceStatus,
    public comment?: string,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public previous?: IInvoiceHistory,
    public next?: IInvoiceHistory,
    public invoice?: IInvoice
  ) {}
}
