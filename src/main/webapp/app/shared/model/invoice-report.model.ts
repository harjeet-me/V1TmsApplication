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
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
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
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public invoices?: IInvoice[]
  ) {}
}
