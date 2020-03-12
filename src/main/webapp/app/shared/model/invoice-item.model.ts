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
  createdOn?: Moment;
  createdBy?: string;
  updatedOn?: Moment;
  updatedBy?: string;
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
    public createdOn?: Moment,
    public createdBy?: string,
    public updatedOn?: Moment,
    public updatedBy?: string,
    public invoice?: IInvoice
  ) {}
}
