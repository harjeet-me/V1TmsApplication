import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IProductItem {
  id?: number;
  itemName?: string;
  description?: string;
  defaultQty?: number;
  price?: number;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  customers?: ICustomer[];
}

export class ProductItem implements IProductItem {
  constructor(
    public id?: number,
    public itemName?: string,
    public description?: string,
    public defaultQty?: number,
    public price?: number,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public customers?: ICustomer[]
  ) {}
}
