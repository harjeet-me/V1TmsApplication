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
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
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
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public carrier?: ICarrier
  ) {}
}
