import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IProductItem {
  id?: number;
  itemName?: string;
  description?: string;
  price?: number;
  createdOn?: Moment;
  createdBy?: string;
  updatedOn?: Moment;
  updatedBy?: string;
  customers?: ICustomer[];
}

export class ProductItem implements IProductItem {
  constructor(
    public id?: number,
    public itemName?: string,
    public description?: string,
    public price?: number,
    public createdOn?: Moment,
    public createdBy?: string,
    public updatedOn?: Moment,
    public updatedBy?: string,
    public customers?: ICustomer[]
  ) {}
}
