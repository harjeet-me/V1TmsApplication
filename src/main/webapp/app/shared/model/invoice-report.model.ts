import { Moment } from 'moment';
import { IInvoice } from 'app/shared/model/invoice.model';

export interface IInvoiceReport {
  id?: number;
  customer?: number;
  fromDate?: Moment;
  toDate?: Moment;
  remarks?: string;
  invoiceReportContentType?: string;
  invoiceReport?: any;
  createdOn?: Moment;
  createdBy?: string;
  updatedOn?: Moment;
  updatedBy?: string;
  invoices?: IInvoice[];
}

export class InvoiceReport implements IInvoiceReport {
  constructor(
    public id?: number,
    public customer?: number,
    public fromDate?: Moment,
    public toDate?: Moment,
    public remarks?: string,
    public invoiceReportContentType?: string,
    public invoiceReport?: any,
    public createdOn?: Moment,
    public createdBy?: string,
    public updatedOn?: Moment,
    public updatedBy?: string,
    public invoices?: IInvoice[]
  ) {}
}
