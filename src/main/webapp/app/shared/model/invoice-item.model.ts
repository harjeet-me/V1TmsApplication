import { Moment } from 'moment';
import { IInvoice } from 'app/shared/model/invoice.model';

export interface IInvoiceItem {
  id?: number;
  itemName?: string;
  description?: string;
  qty?: number;
  price?: number;
  discount?: number;
  total?: number;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  invoice?: IInvoice;
}

export class InvoiceItem implements IInvoiceItem {
  constructor(
    public id?: number,
    public itemName?: string,
    public description?: string,
    public qty?: number,
    public price?: number,
    public discount?: number,
    public total?: number,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public invoice?: IInvoice
  ) {}
}
