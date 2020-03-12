import { Moment } from 'moment';
import { ICarrier } from 'app/shared/model/carrier.model';

export interface IInsurance {
  id?: number;
  providerName?: string;
  issueDate?: Moment;
  expiryDate?: Moment;
  policyDocumentContentType?: string;
  policyDocument?: any;
  coverageStatement?: string;
  createdOn?: Moment;
  createdBy?: string;
  updatedOn?: Moment;
  updatedBy?: string;
  carrier?: ICarrier;
}

export class Insurance implements IInsurance {
  constructor(
    public id?: number,
    public providerName?: string,
    public issueDate?: Moment,
    public expiryDate?: Moment,
    public policyDocumentContentType?: string,
    public policyDocument?: any,
    public coverageStatement?: string,
    public createdOn?: Moment,
    public createdBy?: string,
    public updatedOn?: Moment,
    public updatedBy?: string,
    public carrier?: ICarrier
  ) {}
}
